package de.jgroehl.asteromania.control.callbacks;

import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;

public class MenuButtonCallback implements EventCallback
{

	private final GameState state;

	public MenuButtonCallback(GameState state)
	{
		this.state = state;
	}

	@Override
	public void action(BaseGameHandler gameHandler)
	{
		if (gameHandler instanceof AsteromaniaGameHandler)
		{

			((AsteromaniaGameHandler) gameHandler).getSoundManager().playClickSound();
			gameHandler.setGameState(state);
		}
	}
}
