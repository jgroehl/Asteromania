package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;

public class Shot extends GraphicsObject {

	public enum Target {
		ENEMY, PLAYER
	}

	private final float direction;
	private final float shotSpeed;
	private int damage;
	private Target target;

	public Shot(float xPosition, float yPosition, Target target,
			float shotSpeed, Context context, int damage) {
		super(
				xPosition,
				yPosition,
				target.equals(Target.PLAYER) ? de.jgroehl.asteromania.R.drawable.normal_shot_down
						: de.jgroehl.asteromania.R.drawable.normal_shot_up,
				context);
		this.target = target;
		direction = target.equals(Target.PLAYER) ? 1.0f : -1.0f;
		this.shotSpeed = shotSpeed;
		this.damage = damage;
	}

	@Override
	public void update(GameHandler handler) {

		for (Hitable hitable : handler.getHitableObjects()) {
			GraphicsObject go = (GraphicsObject) hitable;
			if (imagesOverlap(getX(), getY(), getRelativeWidth(),
					getRelativeHeight(), go.getX(), go.getY(),
					go.getRelativeWidth(), go.getRelativeHeight())) {
				hitable.getShot(handler, this);
			}
		}

		yPosition = yPosition + (shotSpeed * direction);
		if (yPosition > 1.1f || yPosition < -0.1f) {
			handler.remove(this);
		}

	}

	public int getDamage() {
		return damage;
	}

	public Target getTarget() {
		return target;
	}
}
