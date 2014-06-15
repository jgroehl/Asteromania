package de.jgroehl.asteromania.graphics.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.animated.AnimatedGraphicsObject;
import de.jgroehl.asteromania.graphics.animated.SimpleAnimatedObject;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;
import de.jgroehl.asteromania.sensoryInfo.SensorHandler;

public class SpaceShip extends AnimatedGraphicsObject implements Hitable {

	private static final String TAG = SpaceShip.class.getSimpleName();
	private final SensorHandler sensorHandler;
	private double sensitivity = 1.5;
	private static final int MAX_DEGREE = 90;
	private static final float MINIMUM_PITCH_VALUE = 0.5f;
	private static final int ANIMATION_TIME = 25;
	private static final int IMAGE_FRAMES = 15;
	private static final float BASIC_SHOT_SPEED = 0.01f;
	private int normalFrame = getMaxFrame() / 2;
	private final SimpleAnimatedObject flames;
	private float shotSpeedFactor = 1.0f;

	public SpaceShip(Bitmap graphics, SensorHandler sensorHandler, Resources res) {
		super(0.6f, 0.7f, graphics, IMAGE_FRAMES, ANIMATION_TIME);
		this.sensorHandler = sensorHandler;
		flames = new SimpleAnimatedObject(xPosition, yPosition,
				BitmapFactory.decodeResource(res, R.drawable.fire), 6, 75);
		Log.d(TAG, "Player Object created.");
	}

	@Override
	public void update(GameHandler handler) {

		float pitch = sensorHandler.getPitchAngle();

		if (Math.abs(pitch) > MINIMUM_PITCH_VALUE) {
			movePlayerAccordingToPitch(pitch);
			updatePlayerFrame(pitch);
		} else {
			normalizePlayerFrame();
		}
		flames.setPosition(
				xPosition + getRelativeWidth() / 2 - flames.getRelativeWidth()
						/ 2,
				yPosition + getRelativeHeight() - flames.getRelativeHeight()
						/ 10);
		flames.update(handler);

	}

	@Override
	public void draw(Canvas c) {
		super.draw(c);
		flames.draw(c);
	};

	private void normalizePlayerFrame() {
		if (getFrame() > normalFrame) {
			setFrame(getFrame() - 1);
		} else if (getFrame() < normalFrame) {
			setFrame(getFrame() + 1);
		}
	}

	private void updatePlayerFrame(float pitch) {

		if (pitch > 0) {
			if (getFrame() > 0)
				setFrame(getFrame() - 1);
		} else {
			if (getFrame() < getMaxFrame() - 1)
				setFrame(getFrame() + 1);
		}

	}

	private void movePlayerAccordingToPitch(float pitch) {
		float sign = Math.signum(pitch);

		if (Math.abs(pitch) > MAX_DEGREE) {
			pitch = 2 * MAX_DEGREE - Math.abs(pitch);
		}

		float value = (float) (Math.pow(Math.abs(pitch) / MAX_DEGREE,
				sensitivity));

		xPosition -= value * sign;

		if (xPosition <= SCREEN_MINIMUM - getRelativeWidth() / 2)
			xPosition = SCREEN_MINIMUM - getRelativeWidth() / 2;
		if (xPosition >= SCREEN_MAXIMUM - getRelativeWidth() / 2)
			xPosition = SCREEN_MAXIMUM - getRelativeWidth() / 2;

	}

	public void setSensitivity(float sensitivity) {
		this.sensitivity = sensitivity;
	}

	public float getShotSpeed() {
		return BASIC_SHOT_SPEED * shotSpeedFactor;
	}

	@Override
	public void getShot(GameHandler gameHandler, Shot shot) {
		// TODO:
	}

}
