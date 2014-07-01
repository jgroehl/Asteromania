package de.jgroehl.asteromania.control.callbacks;

import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.control.interfaces.EventCallback;
import de.jgroehl.asteromania.graphics.GameObject.Level;
import de.jgroehl.asteromania.graphics.game.Shot;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.time.Timer;

public class ShotFiredCallback implements EventCallback {

	private final int BASIC_SHOOT_FREQUENCY = 1000;
	private final Timer shotFrequencyTimer = new Timer(BASIC_SHOOT_FREQUENCY);

	@Override
	public void action(GameHandler gameHandler) {
		if (shotFrequencyTimer.isPeriodOver()) {
			gameHandler.getSoundManager().playNormalShotSound();
			Shot shot = new Shot(gameHandler.getPlayer().getX()
					+ gameHandler.getPlayer().getRelativeWidth() * 0.35f,
					gameHandler.getPlayer().getY(), Target.ENEMY, gameHandler
							.getPlayer().getShotSpeed()
							* gameHandler.getPlayerInfo().getShotSpeedFactor(),
					gameHandler.getContext());
			shot.setLevel(Level.BOTTOM);
			gameHandler.add(shot, GameState.MAIN);
			shotFrequencyTimer.reset((int) (BASIC_SHOOT_FREQUENCY / gameHandler
					.getPlayerInfo().getShotFrequencyFactor()));
		}
	}
}
