package de.jgroehl.asteromania;

import android.os.Bundle;
import de.jgroehl.api.AbstractMainActivity;
import de.jgroehl.asteromania.control.GoogleApiHandler;

public class AsteromaniaMainActivity extends AbstractMainActivity {

	private MainGamePanel mainGamePanel;
	public static final boolean DEBUG = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainGamePanel = new MainGamePanel(this, new GoogleApiHandler(getApiClient(), this), this);

		setContentView(mainGamePanel);
	}

	@Override
	public boolean isInDebug() {
		return false;
	}

}
