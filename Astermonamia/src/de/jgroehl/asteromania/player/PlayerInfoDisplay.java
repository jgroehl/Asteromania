package de.jgroehl.asteromania.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GameObject;
import de.jgroehl.asteromania.graphics.GraphicsObject;

public class PlayerInfoDisplay extends GameObject {

	private final PlayerInfo playerInfo;

	private final GraphicsObject coin;

	private Paint textPaint = new Paint();

	public PlayerInfoDisplay(Context context, PlayerInfo playerInfo) {
		super(0.0f, 0.0f, context);
		this.playerInfo = playerInfo;
		coin = new GraphicsObject(0.0f, 0.0f,
				de.jgroehl.asteromania.R.drawable.coin, context) {
			@Override
			public void update(GameHandler gameHandler) {
			}
		};

		textPaint.setColor(Color.YELLOW);
		textPaint.setStyle(Style.FILL);
	}

	@Override
	public void draw(Canvas c) {
		coin.draw(c);
		textPaint.setTextSize(coin.getRelativeHeight() * c.getHeight());
		c.drawText(String.valueOf(playerInfo.getCoins()),
				coin.getRelativeWidth() * c.getWidth(), 0, textPaint);
		Log.d("COINS", ""+playerInfo.getCoins());
	}

	@Override
	public void update(GameHandler gameHandler) {
	}

}
