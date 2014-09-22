package de.jgroehl.asteromania.graphics.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.jgroehl.api.graphics.ui.Button;
import de.jgroehl.asteromania.control.PlayerInfo;
import de.jgroehl.asteromania.control.callbacks.BuyItemCallback;
import de.jgroehl.asteromania.control.callbacks.BuyItemCallback.ItemType;
import de.jgroehl.asteromania.control.callbacks.PurchaseItemCallback;
import de.jgroehl.asteromania.control.callbacks.PurchaseItemCallback.PurchaseType;

public class BuyItemShopButton extends Button {

	private final ItemType itemType;
	private final PlayerInfo playerInfo;
	private final PurchaseType purchaseType;
	private Paint textPaint;

	public BuyItemShopButton(int iconID, float xPosition, float yPosition,
			float width, float height, ItemType itemType, Context context,
			PlayerInfo playerInfo) {
		super(BitmapFactory.decodeResource(context.getResources(), iconID),
				xPosition, yPosition, width, height, new BuyItemCallback(
						itemType), context);

		this.playerInfo = playerInfo;
		this.itemType = itemType;
		this.purchaseType = null;
	}

	public BuyItemShopButton(int iconID, float xPosition, float yPosition,
			float width, float height, PurchaseType purchaseType,
			Context context, PlayerInfo playerInfo) {
		super(BitmapFactory.decodeResource(context.getResources(), iconID),
				xPosition, yPosition, width, height, new PurchaseItemCallback(
						purchaseType, context), context);

		this.itemType = null;
		this.playerInfo = playerInfo;
		this.purchaseType = purchaseType;
	}

	@Override
	public void draw(Canvas c) {
		super.draw(c);
		if (textPaint == null) {
			textPaint = new Paint();
			textPaint.setColor(Color.rgb(200, 200, 50));
			textPaint.setTextSize(relativeHeight / 3 * c.getHeight());
		}
		c.drawText(
				itemType == null ? context.getResources().getString(
						purchaseType.textId) : itemType.getText(context
						.getResources()), (xPosition + relativeWidth * 1.05f)
						* c.getWidth(),
				(yPosition + (relativeHeight * 1.5f / 5)) * c.getHeight(),
				textPaint);
		c.drawText(
				"("
						+ (itemType == null ? purchaseType.cost
								+ " "
								+ context.getResources().getString(
										de.jgroehl.asteromania.R.string.coins)
								: itemType.getCost(playerInfo)
										+ " "
										+ context
												.getResources()
												.getString(
														de.jgroehl.asteromania.R.string.coins))
						+ ")",
				(xPosition + relativeWidth * 1.05f) * c.getWidth(),
				(yPosition + (relativeHeight * 3.5f / 5)) * c.getHeight(),
				textPaint);
	}
}
