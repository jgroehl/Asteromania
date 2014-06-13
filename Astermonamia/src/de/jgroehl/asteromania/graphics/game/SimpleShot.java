package de.jgroehl.asteromania.graphics.game;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GraphicsObject;

public class SimpleShot extends GraphicsObject {

	public enum Target {
		ENEMY, PLAYER
	}

	private final float direction;
	private final float shotSpeed;

	public SimpleShot(float xPosition, float yPosition, Target target,
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
										de.jgroehl.asteromania.R.drawable.normal_shot_up));
		direction = target.equals(Target.PLAYER) ? 1.0f : -1.0f;
		this.shotSpeed = shotSpeed;
	}

	@Override
	public void update(GameHandler handler) {

		yPosition = yPosition + (shotSpeed * direction);
		if (yPosition > 1.1f || yPosition < -0.1f){
			handler.remove(this);
		}

	}
}
