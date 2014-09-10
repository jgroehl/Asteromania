package de.jgroehl.asteromania.graphics.starfield;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.PlayerInfo;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.graphics.ui.Highscore;
import de.jgroehl.asteromania.time.Timer;

public class GameOverDisplay extends GraphicsObject {

	private final PlayerInfo playerInfo;
	private final Highscore highscore;
	private Paint gameOverTextPaint;
	private Paint scorePaint;
	private final Timer blinkingTimer = new Timer(500);
	private boolean showText;

	public GameOverDisplay(Context context, PlayerInfo playerInfo,
			Highscore highscore) {
		super(0, 0, 1, 1, context);

		this.playerInfo = playerInfo;
		this.highscore = highscore;
	}

	@Override
	public void draw(Canvas c) {
		if (gameOverTextPaint == null) {
			gameOverTextPaint = new Paint();
			gameOverTextPaint.setColor(Color.rgb(210, 210, 140));
			gameOverTextPaint.setTextSize(c.getHeight() * 0.2f);
		}
		if (scorePaint == null) {
			scorePaint = new Paint();
			scorePaint.setColor(Color.rgb(255, 255, 150));
			scorePaint.setTextSize(c.getHeight() * 0.05f);
		}

		c.drawText(
				context.getResources().getString(
						de.jgroehl.asteromania.R.string.game_over),
				c.getWidth() * 0.1f, c.getHeight() * 0.3f, gameOverTextPaint);
		c.drawText(
				context.getResources().getString(
						de.jgroehl.asteromania.R.string.your_score)
						+ ": " + playerInfo.getLastHighscore(),
				c.getWidth() * 0.2f, c.getHeight() * 0.45f, scorePaint);
		if (highscore.isNewHighscore(playerInfo.getLastHighscore()) && showText) {
			c.drawText(
					context.getResources().getString(
							de.jgroehl.asteromania.R.string.new_highscore)
							+ "!", c.getWidth() * 0.2f, c.getHeight() * 0.6f,
					scorePaint);
		}

		if (blinkingTimer.isPeriodOver()) {
			showText = !showText;
		}
	}

	@Override
	public void update(GameHandler gameHandler) {
		// Nothing to do here

	}

}
