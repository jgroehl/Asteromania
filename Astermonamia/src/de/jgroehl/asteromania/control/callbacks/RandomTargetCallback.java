package de.jgroehl.asteromania.control.callbacks;

import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.graphics.bullets.TargetedShot;

public class RandomTargetCallback implements EventCallback
{

	@Override
	public void action(BaseGameHandler gameHandler)
	{
		gameHandler.add(TargetedShot.createShotWithRandomTarget((AsteromaniaGameHandler) gameHandler), GameState.MAIN);
	}
}
