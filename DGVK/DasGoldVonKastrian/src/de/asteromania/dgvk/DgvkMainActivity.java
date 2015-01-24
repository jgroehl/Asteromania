package de.asteromania.dgvk;

import android.content.Intent;
import android.os.Bundle;
import de.asteromania.dgvk.control.DgvkGameHandler;
import de.jgroehl.api.activities.AbstractSimpleActivity;
import de.jgroehl.api.control.GameState;

public class DgvkMainActivity extends AbstractSimpleActivity
{

	private static final String INTENT_LOGIN = "de.asteromania.dgvk.LOGIN";

	private DgvkGamePanel mainGamePanel;

	private DgvkGameHandler gameHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		gameHandler = new DgvkGameHandler(GameState.STARTING_SCREEN, this);
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

}
