package de.asteromania.dgvk.activities;

import android.os.Bundle;
import de.asteromania.dgvk.DgvkGamePanel;
import de.asteromania.dgvk.control.DgvkGameHandler;
import de.asteromania.dgvk.control.UserDataHandler;
import de.asteromania.dgvk.net.DgvkUrlProperties;
import de.jgroehl.api.activities.AbstractSimpleActivity;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.crypto.CryptoHandler;
import de.jgroehl.api.net.HttpPostTask;

public class DgvkMainActivity extends AbstractSimpleActivity
{

	private DgvkGamePanel mainGamePanel;

	private UserDataHandler userDataHandler;

	private DgvkGameHandler gameHandler;

	private CryptoHandler cryptoHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		cryptoHandler = new CryptoHandler(this);
		userDataHandler = new UserDataHandler(cryptoHandler, this);
		gameHandler = new DgvkGameHandler(userDataHandler, this);
		mainGamePanel = new DgvkGamePanel(this, this, gameHandler);
		setContentView(mainGamePanel);
	}

	@Override
	public boolean isInDebug()
	{
		return false;
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (userDataHandler.getLoggedInUser() != null)
		{
			new HttpPostTask(DgvkUrlProperties.logoutUrl(), null, userDataHandler.getLoggedInUser().getUsername(), null)
					.execute();
			userDataHandler.logoutUser();
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		if (userDataHandler.getLoggedInUser() != null)
			gameHandler.setGameState(GameState.MAIN);
	}

}
