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
		AsteromaniaGameHandler handler = (AsteromaniaGameHandler) gameHandler;
		if (handler.getPlayerInfo().getAmmo() > 0)
		{
			TargetedShot shot = TargetedShot.createShotWithRandomTarget((AsteromaniaGameHandler) gameHandler);
			if (shot != null)
			{
				((AsteromaniaGameHandler) gameHandler).getSoundManager().playEnemyShotSound();
				gameHandler.add(shot, GameState.MAIN);
				handler.getPlayerInfo().decrementAmmo();
			}
		}
	}
}
