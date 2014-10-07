package de.jgroehl.asteromania.control.callbacks;

import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;

public class FromCheckpointCallback extends MenuButtonCallback {

	public FromCheckpointCallback() {
		super(GameState.MAIN);
	}

	@Override
	public void action(BaseGameHandler gameHandler) {
		if (gameHandler instanceof AsteromaniaGameHandler) {
			AsteromaniaGameHandler asteromaniaGameHandler = ((AsteromaniaGameHandler) gameHandler);
			asteromaniaGameHandler.getPlayerInfo().resetToCheckpoint();
		}
		super.action(gameHandler);
	}

}
