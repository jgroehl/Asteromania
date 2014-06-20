package de.jgroehl.asteromania;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private static final boolean DEBUG = true;
	private MainGamePanel mainGamePanel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Executing onCreate of MainActivity...");
		super.onCreate(savedInstanceState);
		
		mainGamePanel = new MainGamePanel(this, DEBUG);

		setContentView(mainGamePanel);

		Toast.makeText(this, "Welcome to Asteromania", Toast.LENGTH_SHORT)
				.show();

		Log.d(TAG, "Executing onCreate of MainActivity...[Done]");
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "On Pause called.");
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying Application...");
		mainGamePanel.getGameHandler().getPlayerInfo().savePlayerInfo();
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping Application...");
		super.onStop();
	}
}
