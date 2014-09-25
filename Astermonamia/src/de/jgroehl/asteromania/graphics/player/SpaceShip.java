package de.jgroehl.asteromania.graphics.player;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.AbstractDamagingAbility;
import de.jgroehl.api.graphics.Target;
import de.jgroehl.api.graphics.animated.AnimatedGraphicsObject;
import de.jgroehl.api.graphics.animated.SimpleAnimatedObject;
import de.jgroehl.api.graphics.interfaces.Hitable;
import de.jgroehl.api.utils.SensorHandler;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.control.PlayerInfo;
import de.jgroehl.asteromania.graphics.ShieldGenerator;

public class SpaceShip extends AnimatedGraphicsObject implements Hitable {

	private static final float RELATIVE_WIDTH = 0.12f;
	private static final String TAG = SpaceShip.class.getSimpleName();
	private final SensorHandler sensorHandler;
	private static final int MAX_DEGREE = 90;
	private static final float MINIMUM_PITCH_VALUE = 0.5f;
	private static final int ANIMATION_TIME = 25;
	private static final int IMAGE_FRAMES = 15;
	private static final float BASIC_SHOT_SPEED = 0.01f;
	private static final float MAX_SHIP_SPEED = 0.006f;
	public static final int BASIC_SHOT_DAMAGE = 1;
	private int normalFrame = getMaxFrame() / 2;
	private final SimpleAnimatedObject[] flames = new SimpleAnimatedObject[2];
	private final ShieldGenerator shield;

	public SpaceShip(SensorHandler sensorHandler, Context context,
			PlayerInfo playerInfo) {
		super(0.6f, 0.7f, R.drawable.spaceship2, RELATIVE_WIDTH, IMAGE_FRAMES,
				ANIMATION_TIME, context);
		this.sensorHandler = sensorHandler;
		flames[0] = new SimpleAnimatedObject(xPosition, yPosition,
				R.drawable.fire, 0.02f, 6, 75, context);
		flames[1] = new SimpleAnimatedObject(xPosition, yPosition,
				R.drawable.fire, 0.02f, 6, 75, context);
		shield = new ShieldGenerator(xPosition, xPosition, context, playerInfo);
		Log.d(TAG, "Player Object created.");
	}

	@Override
	public void update(BaseGameHandler handler) {

		float pitch = sensorHandler.getPitchAngle();

		if (Math.abs(pitch) > MINIMUM_PITCH_VALUE) {
			movePlayerAccordingToPitch(pitch, handler);
			updatePlayerFrame(pitch);
		} else {
			normalizePlayerFrame();
		}
		flames[0].setPosition(xPosition + getRelativeWidth() * 5 / 8
				- flames[0].getRelativeWidth() / 2, yPosition
				+ getRelativeHeight() - flames[0].getRelativeHeight() / 5);
		flames[0].update(handler);
		flames[1].setPosition(xPosition + getRelativeWidth() * 3 / 9
				- flames[1].getRelativeWidth() / 2, yPosition
				+ getRelativeHeight() - flames[1].getRelativeHeight() / 5);
		flames[1].update(handler);
		shield.setPosition(
				xPosition + getRelativeWidth() / 2 - shield.getRelativeWidth()
						/ 2,
				yPosition + getRelativeHeight() / 2
						- shield.getRelativeHeight() / 2);
		shield.update(handler);
	}

	@Override
	public void draw(Canvas c) {
		super.draw(c);
		flames[0].draw(c);
		flames[1].draw(c);
		shield.draw(c);
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

	private void movePlayerAccordingToPitch(float pitch, BaseGameHandler handler) {
		float sign = Math.signum(pitch);

		if (Math.abs(pitch) > MAX_DEGREE) {
			pitch = 2 * MAX_DEGREE - Math.abs(pitch);
		}

		float value = (float) (Math.pow(Math.abs(pitch) / MAX_DEGREE, 1.4f));

		if (handler instanceof AsteromaniaGameHandler) {
			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) handler;

			if (value > MAX_SHIP_SPEED
					* asteromaniaGameHandler.getPlayerInfo()
							.getMaxSpeedFactor())
				value = MAX_SHIP_SPEED
						* asteromaniaGameHandler.getPlayerInfo()
								.getMaxSpeedFactor();
		}

		xPosition -= value * sign;

		if (xPosition <= SCREEN_MINIMUM - getRelativeWidth() / 2)
			xPosition = SCREEN_MINIMUM - getRelativeWidth() / 2;
		if (xPosition >= SCREEN_MAXIMUM - getRelativeWidth() / 2)
			xPosition = SCREEN_MAXIMUM - getRelativeWidth() / 2;

	}

	public float getShotSpeed() {
		return BASIC_SHOT_SPEED;
	}

	@Override
	public void getShot(BaseGameHandler gameHandler,
			AbstractDamagingAbility shot) {

		if (gameHandler instanceof AsteromaniaGameHandler) {
			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;

			if (shot.getTarget() == Target.PLAYER) {
				if (shield.isActive()) {
					asteromaniaGameHandler.getSoundManager()
							.playShieldHitSound();
					shield.minorHit();
				} else {
					asteromaniaGameHandler.getSoundManager()
							.playPlayerHitSound();
					asteromaniaGameHandler.getPlayerInfo().resetScoreBonus();
					asteromaniaGameHandler
							.getPlayerInfo()
							.getHealthPoints()
							.setCurrentValue(
									asteromaniaGameHandler.getPlayerInfo()
											.getHealthPoints()
											.getCurrentValue()
											- shot.getDamage());
					if (asteromaniaGameHandler.getPlayerInfo()
							.getHealthPoints().getCurrentValue() <= 0)
						asteromaniaGameHandler.gameLost();
				}
				gameHandler.remove(shot);
			}
		}
	}

	public void getHitByAsteroid(AsteromaniaGameHandler gameHandler, int damage) {
		if (shield.isActive()) {
			gameHandler.getSoundManager().playShieldHitSound();
			shield.majorHit();
		} else {
			gameHandler.getSoundManager().playPlayerHitSound();
			gameHandler.getPlayerInfo().resetScoreBonus();
			gameHandler
					.getPlayerInfo()
					.getHealthPoints()
					.setCurrentValue(
							gameHandler.getPlayerInfo().getHealthPoints()
									.getCurrentValue()
									- damage);
			if (gameHandler.getPlayerInfo().getHealthPoints().getCurrentValue() <= 0)
				gameHandler.gameLost();
		}
	}

	public int getShotDamage() {
		return BASIC_SHOT_DAMAGE;
	}

	public void addShieldSeconds(int newLevelShieldSeconds) {
		shield.addShieldSeconds(newLevelShieldSeconds);
	}

	@Override
	public boolean isAlive() {
		return true;
	}

}
