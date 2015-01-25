package de.asteromania.dgvk.control;

import de.asteromania.dgvk.activities.DgvkMainActivity;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;

public class DgvkGameHandler extends BaseGameHandler
{

	public DgvkGameHandler(GameState state, UserDataHandler userDataHandler, DgvkMainActivity context)
	{
		super(state, context);
	}

	public DgvkMainActivity getContext()
	{
		return (DgvkMainActivity) super.getContext();
	}

}
