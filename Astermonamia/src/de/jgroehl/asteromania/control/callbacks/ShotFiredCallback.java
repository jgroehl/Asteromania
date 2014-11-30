package de.jgroehl.asteromania.control.callbacks;

import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.api.graphics.GameObject.Level;
import de.jgroehl.api.graphics.Target;
import de.jgroehl.api.time.Timer;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.graphics.bullets.Shot;

public class ShotFiredCallback implements EventCallback
{

	public static final int BASIC_SHOOT_FREQUENCY = 500;
	private final Timer shotFrequencyTimer = new Timer(BASIC_SHOOT_FREQUENCY);

	@Override
	public void action(BaseGameHandler gameHandler)
	{
		if (gameHandler instanceof AsteromaniaGameHandler)
		{

			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;

			if (shotFrequencyTimer.isPeriodOver())
			{
				asteromaniaGameHandler.getSoundManager().playNormalShotSound();
				if (asteromaniaGameHandler.getPlayerInfo().purchasedTripleShot())
				{

					Shot shot = new Shot(asteromaniaGameHandler.getPlayer().getX()
							+ asteromaniaGameHandler.getPlayer().getRelativeWidth() * 0.08f, asteromaniaGameHandler
							.getPlayer().getY() + asteromaniaGameHandler.getPlayer().getRelativeHeight() * 0.4f,
							Target.ENEMY, asteromaniaGameHandler.getPlayer().getShotSpeed()
									* asteromaniaGameHandler.getPlayerInfo().getShotSpeedFactor(),
							asteromaniaGameHandler.getContext(), asteromaniaGameHandler.getPlayer().getShotDamage()
									+ asteromaniaGameHandler.getPlayerInfo().getBonusDamage(), null);
					shot.setLevel(Level.BOTTOM);
					asteromaniaGameHandler.add(shot, GameState.MAIN);

					Shot shot2 = new Shot(asteromaniaGameHandler.getPlayer().getX()
							+ asteromaniaGameHandler.getPlayer().getRelativeWidth() * 0.48f, asteromaniaGameHandler
							.getPlayer().getY(), Target.ENEMY, asteromaniaGameHandler.getPlayer().getShotSpeed()
							* asteromaniaGameHandler.getPlayerInfo().getShotSpeedFactor(),
							asteromaniaGameHandler.getContext(), asteromaniaGameHandler.getPlayer().getShotDamage()
									+ asteromaniaGameHandler.getPlayerInfo().getBonusDamage(), null);
					shot2.setLevel(Level.BOTTOM);
					asteromaniaGameHandler.add(shot2, GameState.MAIN);

					Shot shot3 = new Shot(asteromaniaGameHandler.getPlayer().getX()
							+ asteromaniaGameHandler.getPlayer().getRelativeWidth() * 0.88f, asteromaniaGameHandler
							.getPlayer().getY() + asteromaniaGameHandler.getPlayer().getRelativeHeight() * 0.4f,
							Target.ENEMY, asteromaniaGameHandler.getPlayer().getShotSpeed()
									* asteromaniaGameHandler.getPlayerInfo().getShotSpeedFactor(),
							asteromaniaGameHandler.getContext(), asteromaniaGameHandler.getPlayer().getShotDamage()
									+ asteromaniaGameHandler.getPlayerInfo().getBonusDamage(), null);
					shot3.setLevel(Level.BOTTOM);
					asteromaniaGameHandler.add(shot3, GameState.MAIN);

				}
				else if (asteromaniaGameHandler.getPlayerInfo().purchasedDoubleShot())
				{

					Shot shot = new Shot(asteromaniaGameHandler.getPlayer().getX()
							+ asteromaniaGameHandler.getPlayer().getRelativeWidth() * 0.28f, asteromaniaGameHandler
							.getPlayer().getY() + asteromaniaGameHandler.getPlayer().getRelativeHeight() * 0.2f,
							Target.ENEMY, asteromaniaGameHandler.getPlayer().getShotSpeed()
									* asteromaniaGameHandler.getPlayerInfo().getShotSpeedFactor(),
							asteromaniaGameHandler.getContext(), asteromaniaGameHandler.getPlayer().getShotDamage()
									+ asteromaniaGameHandler.getPlayerInfo().getBonusDamage(), null);
					shot.setLevel(Level.BOTTOM);
					asteromaniaGameHandler.add(shot, GameState.MAIN);

					Shot shot2 = new Shot(asteromaniaGameHandler.getPlayer().getX()
							+ asteromaniaGameHandler.getPlayer().getRelativeWidth() * 0.58f, asteromaniaGameHandler
							.getPlayer().getY() + asteromaniaGameHandler.getPlayer().getRelativeHeight() * 0.2f,
							Target.ENEMY, asteromaniaGameHandler.getPlayer().getShotSpeed()
									* asteromaniaGameHandler.getPlayerInfo().getShotSpeedFactor(),
							asteromaniaGameHandler.getContext(), asteromaniaGameHandler.getPlayer().getShotDamage()
									+ asteromaniaGameHandler.getPlayerInfo().getBonusDamage(), null);
					shot2.setLevel(Level.BOTTOM);
					asteromaniaGameHandler.add(shot2, GameState.MAIN);

				}
				else
				{
					Shot shot = new Shot(asteromaniaGameHandler.getPlayer().getX()
							+ asteromaniaGameHandler.getPlayer().getRelativeWidth() * 0.48f, asteromaniaGameHandler
							.getPlayer().getY(), Target.ENEMY, asteromaniaGameHandler.getPlayer().getShotSpeed()
							* asteromaniaGameHandler.getPlayerInfo().getShotSpeedFactor(),
							asteromaniaGameHandler.getContext(), asteromaniaGameHandler.getPlayer().getShotDamage()
									+ asteromaniaGameHandler.getPlayerInfo().getBonusDamage(), null);
					shot.setLevel(Level.BOTTOM);
					asteromaniaGameHandler.add(shot, GameState.MAIN);
				}
				shotFrequencyTimer.reset((int) (BASIC_SHOOT_FREQUENCY / asteromaniaGameHandler.getPlayerInfo()
						.getShotFrequencyFactor()));
			}
		}
	}
}
