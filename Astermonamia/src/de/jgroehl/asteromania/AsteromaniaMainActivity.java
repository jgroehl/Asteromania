package de.jgroehl.asteromania;

import android.os.Bundle;
import android.widget.Toast;
import de.jgroehl.api.activities.AbstractGooglePlayGamesLoginActivity;
import de.jgroehl.asteromania.control.GoogleApiHandler;

public class AsteromaniaMainActivity extends
		AbstractGooglePlayGamesLoginActivity {

	private AsteromaniaGamePanel mainGamePanel;
	public static final boolean DEBUG = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainGamePanel = new AsteromaniaGamePanel(this, new GoogleApiHandler(
				getApiClient(), this), this);
		Toast.makeText(this, "Welcome to Asteromania", Toast.LENGTH_LONG)
				.show();
		setContentView(mainGamePanel);
	}

	@Override
	public boolean isInDebug() {
		return false;
	}

}
