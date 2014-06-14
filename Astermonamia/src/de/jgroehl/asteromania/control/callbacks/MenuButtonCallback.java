package de.jgroehl.asteromania.control.callbacks;

import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.control.interfaces.EventCallback;

public class MenuButtonCallback implements EventCallback {

	private final GameState state;

	public MenuButtonCallback(GameState state) {
		this.state = state;
	}

	@Override
	public void action(GameHandler gameHandler) {
		gameHandler.getSoundManager().playClickSound();
		gameHandler.setState(state);

	}

}
