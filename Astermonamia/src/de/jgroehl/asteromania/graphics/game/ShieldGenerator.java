package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.control.PlayerInfo;
import de.jgroehl.asteromania.graphics.animated.SimpleAnimatedObject;
import de.jgroehl.asteromania.time.Timer;

public class ShieldGenerator extends SimpleAnimatedObject {

	private enum ShieldState {
		ACTIVE, INACTIVE, DAMAGED, UNAVAILABLE
	}

	private static final String TAG = ShieldGenerator.class.getSimpleName();

	private static final float RELATIVE_WIDTH = 0.15f;
	private final PlayerInfo playerInfo;
	private ShieldState state;
	private final Timer secondTimer = new Timer(1000);
	private final Timer showTimer = new Timer(250);
	private boolean showing;

	public ShieldGenerator(float xPosition, float yPosition, Context context,
			PlayerInfo playerInfo) {
		super(xPosition, yPosition, R.drawable.shield_small, RELATIVE_WIDTH, 9,
				100, context);

		this.playerInfo = playerInfo;

		state = playerInfo.isShieldGeneratorPresent() ? (playerInfo
				.getShieldSeconds() > 0 ? ShieldState.ACTIVE
				: ShieldState.INACTIVE) : ShieldState.UNAVAILABLE;

		Log.d(TAG, "Setting shield to state: " + state);
	}

	@Override
	public void update(GameHandler gameHandler) {
		super.update(gameHandler);

		if (isActive() && gameHandler.getGameState() == GameState.MAIN
				&& playerInfo.getShieldSeconds() > 0
				&& secondTimer.isPeriodOver()) {
			playerInfo.setShieldSeconds(playerInfo.getShieldSeconds() - 1);
		}

		if (isActive() && playerInfo.getShieldSeconds() <= 0)
			state = ShieldState.INACTIVE;
	}

	@Override
	public void draw(Canvas c) {
		if (state == ShieldState.ACTIVE)
			super.draw(c);
		else if (state == ShieldState.DAMAGED) {
			if (showTimer.isPeriodOver()) {
				showing = !showing;
			}
			if (showing)
				super.draw(c);
		}
	}

	public void minorHit() {
		if (state == ShieldState.ACTIVE) {
			state = ShieldState.DAMAGED;
		} else if (state == ShieldState.DAMAGED) {
			majorHit();
		}
	}

	public void majorHit() {
		if (isActive()) {
			playerInfo.setShieldSeconds(0);
			state = ShieldState.INACTIVE;
		}
	}

	public void addShieldSeconds(int seconds) {
		if (state != ShieldState.UNAVAILABLE) {
			if (seconds < 0)
				throw new IllegalArgumentException(
						"Added shield seconds must be positive");
			state = ShieldState.ACTIVE;
			playerInfo
					.setShieldSeconds(playerInfo.getShieldSeconds() + seconds);
			Log.d(TAG, "Added " + seconds + " seconds to the shield");
		} else {
			if (playerInfo.isShieldGeneratorPresent()) {
				state = ShieldState.INACTIVE;
				addShieldSeconds(seconds);
			}
		}
	}

	public boolean isActive() {
		return state == ShieldState.ACTIVE || state == ShieldState.DAMAGED;
	}

}
