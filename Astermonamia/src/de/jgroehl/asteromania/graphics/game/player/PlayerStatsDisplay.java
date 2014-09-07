package de.jgroehl.asteromania.graphics.game.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.PlayerInfo;
import de.jgroehl.asteromania.control.callbacks.ShotFiredCallback;
import de.jgroehl.asteromania.graphics.GameObject;
import de.jgroehl.asteromania.graphics.animated.AnimatedGraphicsObject;
import de.jgroehl.asteromania.graphics.animated.SimpleAnimatedObject;

public class PlayerStatsDisplay extends GameObject {

	private final PlayerInfo playerInfo;
	private final AnimatedGraphicsObject spaceship;
	private Paint headingPaint;
	private Paint textPaint;

	public PlayerStatsDisplay(PlayerInfo playerInfo, Context context) {
		super(0, 0, context);
		this.playerInfo = playerInfo;

		spaceship = new SimpleAnimatedObject(0.05f, 0.3f,
				de.jgroehl.asteromania.R.drawable.rotating_spaceship, 15, 100,
				context);
	}

	@Override
	public void draw(Canvas c) {

		if (headingPaint == null) {
			headingPaint = new Paint();
			headingPaint.setColor(Color.rgb(250, 200, 100));
			headingPaint.setTextSize(c.getHeight() / 10);
		}
		if (textPaint == null) {
			textPaint = new Paint();
			textPaint.setColor(Color.rgb(250, 250, 150));
			textPaint.setTextSize(c.getHeight() / 20);
		}

		c.drawText(
				context.getResources().getString(
						de.jgroehl.asteromania.R.string.spaceship_statistics),
				0.2f * c.getWidth(), 0.15f * c.getHeight(), headingPaint);
		spaceship.draw(c);
		c.drawText(
				context.getResources().getString(
						de.jgroehl.asteromania.R.string.lifepoints)
						+ ": "
						+ playerInfo.getHealthPoints().getCurrentValue()
						+ " / " + playerInfo.getHealthPoints().getMaximum(),
				0.4f * c.getWidth(), 0.3f * c.getHeight(), textPaint);
		c.drawText(
				context.getResources().getString(
						de.jgroehl.asteromania.R.string.bonus_damage)
						+ ": " + playerInfo.getBonusDamage(),
				0.4f * c.getWidth(), 0.4f * c.getHeight(), textPaint);
		c.drawText(
				context.getResources().getString(
						de.jgroehl.asteromania.R.string.speed)
						+ ": "
						+ (int) (playerInfo.getMaxSpeedFactor() * 100)
						+ "%", 0.4f * c.getWidth(), 0.5f * c.getHeight(),
				textPaint);
		c.drawText(
				context.getResources().getString(
						de.jgroehl.asteromania.R.string.shot_speed)
						+ ": "
						+ (int) (playerInfo.getShotSpeedFactor() * 100)
						+ "%", 0.4f * c.getWidth(), 0.6f * c.getHeight(),
				textPaint);
		c.drawText(
				context.getResources().getString(
						de.jgroehl.asteromania.R.string.shot_frequency)
						+ ": "
						+ (int) (playerInfo.getShotFrequencyFactor() * 100)
						+ "%", 0.4f * c.getWidth(), 0.7f * c.getHeight(),
				textPaint);
		c.drawText(context.getResources().getString(R.string.dps) + ": "
				+ calculateDps(), 0.05f * c.getWidth(), 0.8f * c.getHeight(),
				textPaint);

	}

	private int calculateDps() {
		return (int) ((SpaceShip.BASIC_SHOT_DAMAGE + playerInfo
				.getBonusDamage()) / (ShotFiredCallback.BASIC_SHOOT_FREQUENCY
				/ playerInfo.getShotFrequencyFactor() / 1000));
	}

	@Override
	public void update(GameHandler gameHandler) {
		spaceship.update(gameHandler);
	}

}
