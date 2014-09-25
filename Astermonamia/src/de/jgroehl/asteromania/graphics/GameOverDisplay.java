package de.jgroehl.asteromania.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.graphics.ui.Alignment;
import de.jgroehl.api.graphics.ui.Label;
import de.jgroehl.api.time.Timer;
import de.jgroehl.asteromania.control.PlayerInfo;
import de.jgroehl.asteromania.graphics.ui.Highscore;

public class GameOverDisplay extends GraphicsObject {

	private final PlayerInfo playerInfo;
	private final Highscore highscore;
	private final Label gameOverTextPaint;
	private final Label scorePaint;
	private final Label newHighscoreLabel;
	private final Timer blinkingTimer = new Timer(500);
	private boolean showText;

	public GameOverDisplay(Context context, PlayerInfo playerInfo,
			Highscore highscore) {
		super(0, 0, 1, 1, context);

		gameOverTextPaint = new Label(context.getResources().getString(
				de.jgroehl.asteromania.R.string.game_over), 0.1f, 0.3f, context);
		gameOverTextPaint.setTextColor(Color.rgb(210, 210, 140));
		gameOverTextPaint.setTextHeight(0.2f);
		gameOverTextPaint.setAlignment(Alignment.CENTER_HORIZONTALLY);

		scorePaint = new Label("", 0.2f, 0.45f, context);
		scorePaint.setTextColor(Color.rgb(255, 255, 150));
		scorePaint.setTextHeight(0.05f);

		newHighscoreLabel = new Label(context.getResources().getString(
				de.jgroehl.asteromania.R.string.new_highscore)
				+ "!", 0.2f, 0.6f, context);
		newHighscoreLabel.setTextHeight(0.05f);
		newHighscoreLabel.setTextColor(Color.rgb(255, 255, 150));

		this.playerInfo = playerInfo;
		this.highscore = highscore;
	}

	@Override
	public void draw(Canvas c) {

		gameOverTextPaint.draw(c);
		scorePaint.setText(context.getResources().getString(
				de.jgroehl.asteromania.R.string.your_score)
				+ ": " + playerInfo.getLastHighscore());
		scorePaint.draw(c);
		if (highscore.isNewHighscore(playerInfo.getLastHighscore()) && showText) {
			newHighscoreLabel.draw(c);
		}

		if (blinkingTimer.isPeriodOver()) {
			showText = !showText;
		}
	}

	@Override
	public void update(BaseGameHandler gameHandler) {
		// Nothing to do here

	}

}
