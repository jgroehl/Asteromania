package de.jgroehl.asteromania.graphics.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.jgroehl.asteromania.control.callbacks.BuyItemCallback;
import de.jgroehl.asteromania.control.callbacks.BuyItemCallback.ItemType;
import de.jgroehl.asteromania.player.PlayerInfo;

public class BuyItemShopButton extends Button {

	private final ItemType itemType;
	private final PlayerInfo playerInfo;
	private Paint textPaint;

	public BuyItemShopButton(String name, float xPosition, float yPosition,
			float width, float height, ItemType itemType, Context context,
			PlayerInfo playerInfo) {
		super(name, xPosition, yPosition, width, height, new BuyItemCallback(
				itemType), context);

		this.itemType = itemType;
		this.playerInfo = playerInfo;

	}

	public BuyItemShopButton(int iconID, float xPosition, float yPosition,
			float width, float height, ItemType itemType, Context context,
			PlayerInfo playerInfo) {
		super(BitmapFactory.decodeResource(context.getResources(), iconID),
				xPosition, yPosition, width, height, new BuyItemCallback(
						itemType), context);
		this.itemType = itemType;
		this.playerInfo = playerInfo;
	}

	@Override
	public void draw(Canvas c) {
		super.draw(c);
		if (textPaint == null) {
			textPaint = new Paint();
			textPaint.setColor(Color.rgb(200, 200, 50));
			textPaint.setTextSize(relativeHeight / 3 * c.getHeight());
		}
		c.drawText(itemType.getText(),
				(xPosition + relativeWidth * 1.05f) * c.getWidth(),
				(yPosition + (relativeHeight * 1.5f / 5)) * c.getHeight(),
				textPaint);
		c.drawText("(" + itemType.getCost(playerInfo) + " coins)",
				(xPosition + relativeWidth * 1.05f) * c.getWidth(),
				(yPosition + (relativeHeight * 3.5f / 5)) * c.getHeight(),
				textPaint);
	}
}