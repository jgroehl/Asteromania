package de.jgroehl.api.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public abstract class AbstractSimpleActivity extends Activity {

	private static final String TAG = AbstractSimpleActivity.class
			.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Executing onCreate of MainActivity...");
		super.onCreate(savedInstanceState);
		Log.d(TAG, "Executing onCreate of MainActivity...[Done]");
	}

	@Override
	protected void onStart() {
		Log.d(TAG, "On Start called.");
		super.onStart();
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
		super.onStop();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "Resuming Application...");
		super.onResume();
	}

	public abstract boolean isInDebug();

}
