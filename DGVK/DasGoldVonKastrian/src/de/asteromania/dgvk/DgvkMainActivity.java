package de.asteromania.dgvk;

import android.content.Intent;
import android.os.Bundle;
import de.asteromania.dgvk.control.DgvkGameHandler;
import de.asteromania.dgvk.control.UserDataHandler;
import de.jgroehl.api.activities.AbstractSimpleActivity;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.crypto.CryptoHandler;

public class DgvkMainActivity extends AbstractSimpleActivity
{

	private static final String INTENT_LOGIN = "de.asteromania.dgvk.LOGIN";

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
		gameHandler = new DgvkGameHandler(GameState.STARTING_SCREEN, userDataHandler, this);
		mainGamePanel = new DgvkGamePanel(this, this, gameHandler);
		setContentView(mainGamePanel);
	}

	@Override
	public boolean isInDebug()
	{
		return false;
	}

	public void openLoginScreen()
	{
		startActivity(new Intent(INTENT_LOGIN));
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		userDataHandler.logoutUser();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		if (userDataHandler.getLoggedInUser() != null)
			gameHandler.setState(GameState.MAIN);
	}

}
