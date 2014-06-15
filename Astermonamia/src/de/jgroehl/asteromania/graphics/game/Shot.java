package de.jgroehl.asteromania.graphics.game;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
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

	public Shot(float xPosition, float yPosition, Target target,
			float shotSpeed, Resources res) {
		super(
				xPosition,
				yPosition,
				target.equals(Target.PLAYER) ? BitmapFactory
						.decodeResource(
								res,
								de.jgroehl.asteromania.R.drawable.normal_shot_down)
						: BitmapFactory
								.decodeResource(
										res,
										de.jgroehl.asteromania.R.drawable.normal_shot_up),
				Align.MID_CENTER);
		direction = target.equals(Target.PLAYER) ? 1.0f : -1.0f;
		this.shotSpeed = shotSpeed;
		damage = 1;
	}

	@Override
	public void update(GameHandler handler) {

		for (Hitable hitable : handler.getHitableObjects()) {
			GraphicsObject go = (GraphicsObject) hitable;
			if (imagesOverlap(xPosition, yPosition, getRelativeWidth(),
					getRelativeHeight(), go.getX(), go.getY(),
					go.getRelativeWidth(), go.getRelativeHeight())) {
				hitable.getShot(handler, this);
				handler.remove(this);
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
}
