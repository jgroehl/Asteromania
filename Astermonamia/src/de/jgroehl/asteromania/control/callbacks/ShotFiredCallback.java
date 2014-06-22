package de.jgroehl.asteromania.control.callbacks;

import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.control.interfaces.EventCallback;
import de.jgroehl.asteromania.graphics.GameObject.Level;
import de.jgroehl.asteromania.graphics.game.Shot;
import de.jgroehl.asteromania.graphics.game.Shot.Target;

public class ShotFiredCallback implements EventCallback {

	private final Target target;

	public ShotFiredCallback(Target target) {
		this.target = target;
	}

	@Override
	public void action(GameHandler gameHandler) {
		gameHandler.getSoundManager().playNormalShotSound();
		Shot shot = new Shot(gameHandler.getPlayer().getX()
				+ gameHandler.getPlayer().getRelativeWidth() * 0.35f,
				gameHandler.getPlayer().getY(), target, gameHandler.getPlayer()
						.getShotSpeed(), gameHandler.getContext());
		shot.setLevel(Level.BOTTOM);
		gameHandler.add(shot, GameState.MAIN);
	}

}
