package de.jgroehl.asteromania.control.callbacks;

import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.control.interfaces.EventCallback;
import de.jgroehl.asteromania.graphics.GameObject.Level;
import de.jgroehl.asteromania.graphics.game.Shot;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.time.Timer;

public class ShotFiredCallback implements EventCallback {

	public static final int BASIC_SHOOT_FREQUENCY = 500;
	private final Timer shotFrequencyTimer = new Timer(BASIC_SHOOT_FREQUENCY);

	@Override
	public void action(GameHandler gameHandler) {
		if (shotFrequencyTimer.isPeriodOver()) {
			gameHandler.getSoundManager().playNormalShotSound();
			if (gameHandler.getPlayerInfo().purchasedTripleShot()) {

				Shot shot = new Shot(gameHandler.getPlayer().getX()
						+ gameHandler.getPlayer().getRelativeWidth() * 0.08f,
						gameHandler.getPlayer().getY()
								+ gameHandler.getPlayer().getRelativeHeight()
								* 0.4f, Target.ENEMY, gameHandler.getPlayer()
								.getShotSpeed()
								* gameHandler.getPlayerInfo()
										.getShotSpeedFactor(),
						gameHandler.getContext(), gameHandler.getPlayer()
								.getShotDamage()
								+ gameHandler.getPlayerInfo().getBonusDamage(),
						null);
				shot.setLevel(Level.BOTTOM);
				gameHandler.add(shot, GameState.MAIN);

				Shot shot2 = new Shot(gameHandler.getPlayer().getX()
						+ gameHandler.getPlayer().getRelativeWidth() * 0.48f,
						gameHandler.getPlayer().getY(), Target.ENEMY,
						gameHandler.getPlayer().getShotSpeed()
								* gameHandler.getPlayerInfo()
										.getShotSpeedFactor(),
						gameHandler.getContext(), gameHandler.getPlayer()
								.getShotDamage()
								+ gameHandler.getPlayerInfo().getBonusDamage(),
						null);
				shot2.setLevel(Level.BOTTOM);
				gameHandler.add(shot2, GameState.MAIN);

				Shot shot3 = new Shot(gameHandler.getPlayer().getX()
						+ gameHandler.getPlayer().getRelativeWidth() * 0.88f,
						gameHandler.getPlayer().getY()
								+ gameHandler.getPlayer().getRelativeHeight()
								* 0.4f, Target.ENEMY, gameHandler.getPlayer()
								.getShotSpeed()
								* gameHandler.getPlayerInfo()
										.getShotSpeedFactor(),
						gameHandler.getContext(), gameHandler.getPlayer()
								.getShotDamage()
								+ gameHandler.getPlayerInfo().getBonusDamage(),
						null);
				shot3.setLevel(Level.BOTTOM);
				gameHandler.add(shot3, GameState.MAIN);

			} else if (gameHandler.getPlayerInfo().purchasedDoubleShot()) {

				Shot shot = new Shot(gameHandler.getPlayer().getX()
						+ gameHandler.getPlayer().getRelativeWidth() * 0.28f,
						gameHandler.getPlayer().getY()
								+ gameHandler.getPlayer().getRelativeHeight()
								* 0.2f, Target.ENEMY, gameHandler.getPlayer()
								.getShotSpeed()
								* gameHandler.getPlayerInfo()
										.getShotSpeedFactor(),
						gameHandler.getContext(), gameHandler.getPlayer()
								.getShotDamage()
								+ gameHandler.getPlayerInfo().getBonusDamage(),
						null);
				shot.setLevel(Level.BOTTOM);
				gameHandler.add(shot, GameState.MAIN);

				Shot shot2 = new Shot(gameHandler.getPlayer().getX()
						+ gameHandler.getPlayer().getRelativeWidth() * 0.58f,
						gameHandler.getPlayer().getY()
								+ gameHandler.getPlayer().getRelativeHeight()
								* 0.2f, Target.ENEMY, gameHandler.getPlayer()
								.getShotSpeed()
								* gameHandler.getPlayerInfo()
										.getShotSpeedFactor(),
						gameHandler.getContext(), gameHandler.getPlayer()
								.getShotDamage()
								+ gameHandler.getPlayerInfo().getBonusDamage(),
						null);
				shot2.setLevel(Level.BOTTOM);
				gameHandler.add(shot2, GameState.MAIN);

			} else {
				Shot shot = new Shot(gameHandler.getPlayer().getX()
						+ gameHandler.getPlayer().getRelativeWidth() * 0.48f,
						gameHandler.getPlayer().getY(), Target.ENEMY,
						gameHandler.getPlayer().getShotSpeed()
								* gameHandler.getPlayerInfo()
										.getShotSpeedFactor(),
						gameHandler.getContext(), gameHandler.getPlayer()
								.getShotDamage()
								+ gameHandler.getPlayerInfo().getBonusDamage(),
						null);
				shot.setLevel(Level.BOTTOM);
				gameHandler.add(shot, GameState.MAIN);
			}
			shotFrequencyTimer.reset((int) (BASIC_SHOOT_FREQUENCY / gameHandler
					.getPlayerInfo().getShotFrequencyFactor()));
		}
	}
}
