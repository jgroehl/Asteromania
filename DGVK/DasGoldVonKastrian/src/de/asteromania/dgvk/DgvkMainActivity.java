package de.asteromania.dgvk;

import android.os.Bundle;
import de.jgroehl.api.activities.AbstractSimpleActivity;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;

public class DgvkMainActivity extends AbstractSimpleActivity
{

	public DgvkGamePanel mainGamePanel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mainGamePanel = new DgvkGamePanel(this, this, new BaseGameHandler(GameState.STARTING_SCREEN, this));
		setContentView(mainGamePanel);
	}

	@Override
	public boolean isInDebug()
	{
		return false;
	}

}
