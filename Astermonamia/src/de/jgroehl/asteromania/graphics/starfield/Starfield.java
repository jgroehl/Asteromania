package de.jgroehl.asteromania.graphics.starfield;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GameObject;
import de.jgroehl.asteromania.graphics.interfaces.Updatable;
import de.jgroehl.asteromania.graphics.interfaces.Drawable;

public class Starfield extends GameObject implements Drawable, Updatable {

	private static final float SMALL_STAR_SPEED = 0.002f;
	private static final float MEDIUM_STAR_SPEED = 0.004f;
	private static final float BIG_STAR_SPEED = 0.008f;
	private static final float SMALL_STAR_RADIUS = 0.01f;
	private static final float MEDIUM_STAR_RADIUS = 0.025f;
	private static final float BIG_STAR_RADIUS = 0.05f;
	private static final int AMOUNT_OF_SMALL_STARS = 50;
	private static final int AMOUNT_OF_MEDIUM_STARS = 30;
	private static final int AMOUNT_OF_BIG_STARS = 15;
	private static final int SMALL_STAR_COLOR = Color.rgb(50, 50, 0);
	private static final int MEDIUM_STAR_COLOR = Color.rgb(100, 100, 0);
	private static final int BIG_STAR_COLOR = Color.rgb(150, 150, 0);

	private final StarObject[] stars = new StarObject[AMOUNT_OF_SMALL_STARS
			+ AMOUNT_OF_MEDIUM_STARS + AMOUNT_OF_BIG_STARS];

	public Starfield(Context context) {

		super(0, 0, context);

		for (int i = 0; i < AMOUNT_OF_SMALL_STARS; i++) {
			stars[i] = new StarObject((float) Math.random(),
					(float) Math.random(), SMALL_STAR_RADIUS, SMALL_STAR_COLOR,
					SMALL_STAR_SPEED, context);
		}
		for (int i = 0; i < AMOUNT_OF_MEDIUM_STARS; i++) {
			stars[i + AMOUNT_OF_SMALL_STARS] = new StarObject(
					(float) Math.random(), (float) Math.random(),
					MEDIUM_STAR_RADIUS, MEDIUM_STAR_COLOR, MEDIUM_STAR_SPEED,
					context);
		}
		for (int i = 0; i < AMOUNT_OF_BIG_STARS; i++) {
			stars[i + AMOUNT_OF_SMALL_STARS + AMOUNT_OF_MEDIUM_STARS] = new StarObject(
					(float) Math.random(), (float) Math.random(),
					BIG_STAR_RADIUS, BIG_STAR_COLOR, BIG_STAR_SPEED, context);
		}
	}

	@Override
	public void draw(Canvas c) {

		for (StarObject star : stars) {
			star.draw(c);
		}
	}

	@Override
	public void update(GameHandler gameHandler) {

		for (StarObject star : stars) {
			star.update(gameHandler);
		}

	}

	public void accelerate(float factor) {
		for (StarObject star : stars) {
			star.accelerate(factor);
		}
	}

	public float getAcceleration() {
		return stars[0].getAcceleration();
	}

}
