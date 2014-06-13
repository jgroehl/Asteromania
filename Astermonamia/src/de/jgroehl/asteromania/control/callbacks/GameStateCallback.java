package de.jgroehl.asteromania.control.callbacks;

import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.control.interfaces.EventCallback;

public class GameStateCallback implements EventCallback {

	private final GameState state;

	public GameStateCallback(GameState state) {
		this.state = state;
	}

	@Override
	public void action(GameHandler gamehandler) {
		gamehandler.setState(state);

	}

}
