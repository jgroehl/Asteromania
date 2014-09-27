package de.jgroehl.api.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.games.Games;

public abstract class AbstractGooglePlayGamesLoginActivity extends Activity implements
		ConnectionCallbacks, OnConnectionFailedListener {

	private static final String TAG = AbstractGooglePlayGamesLoginActivity.class
			.getSimpleName();
	private static final int REQUEST_RESOLVE_ERROR = 1001;
	private static final String STATE_RESOLVING_ERROR = "resolving_error";

	private GoogleApiClient apiClient;
	private boolean resolvingError = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Executing onCreate of MainActivity...");
		super.onCreate(savedInstanceState);
		resolvingError = savedInstanceState != null
				&& savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);

		apiClient = new GoogleApiClient.Builder(this).addApi(Games.API)
				.addScope(Games.SCOPE_GAMES).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).build();

		Log.d(TAG, "Resolving error set to: " + resolvingError);

		Toast.makeText(this, "Welcome to Asteromania", Toast.LENGTH_SHORT)
				.show();

		Log.d(TAG, "Executing onCreate of MainActivity...[Done]");
	}

	@Override
	protected void onStart() {
		Log.d(TAG, "On Start called.");
		super.onStart();
		if (!resolvingError)
			apiClient.connect();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "On Pause called.");
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying Application...");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping Application...");
		apiClient.disconnect();
		super.onStop();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "Resuming Application...");
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (result != ConnectionResult.SUCCESS) {
			GooglePlayServicesUtil.getErrorDialog(result, this,
					REQUEST_RESOLVE_ERROR);
		}
		super.onResume();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.d(TAG,
				"Error Connecting to Play Services: " + result.getErrorCode());
		if (resolvingError)
			return;
		else if (result.hasResolution()) {
			Log.d(TAG, "Error has resolution.");
			try {
				resolvingError = true;
				result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
			} catch (SendIntentException e) {
				Log.d(TAG, "try connecting again.");
				apiClient.connect();
			}
		} else {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					REQUEST_RESOLVE_ERROR).show();
			resolvingError = true;
		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		Log.d(TAG, "Connected to Play Services!");
	}

	@Override
	public void onConnectionSuspended(int arg0) {
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_RESOLVE_ERROR) {
			resolvingError = false;
			Log.d(TAG, "Activity ended with code: " + resultCode);
			if (resultCode == RESULT_OK) {
				if (!apiClient.isConnecting() && !apiClient.isConnected()) {
					apiClient.connect();
				}
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(STATE_RESOLVING_ERROR, resolvingError);
	}

	public abstract boolean isInDebug();

	protected GoogleApiClient getApiClient() {
		return apiClient;
	}

}
