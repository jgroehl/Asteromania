package de.jgroehl.asteromania.control;

import android.content.Context;
import android.graphics.Canvas;
import de.jgroehl.asteromania.control.callbacks.BuyItemCallback;
import de.jgroehl.asteromania.control.callbacks.BuyItemCallback.ItemType;
import de.jgroehl.asteromania.graphics.ui.Button;
import de.jgroehl.asteromania.player.PlayerInfo;

public class BuyItemShopButton extends Button {

	private final ItemType itemType;
	private final PlayerInfo playerInfo;

	public BuyItemShopButton(String name, float xPosition, float yPosition,
			float width, float height, ItemType itemType, Context context,
			PlayerInfo playerInfo) {
		super(name, xPosition, yPosition, width, height, new BuyItemCallback(
				itemType), context);

		this.itemType = itemType;
		this.playerInfo = playerInfo;

	}

	@Override
	public void draw(Canvas c) {
		super.draw(c);
		c.drawText("(" + itemType.getCost(playerInfo) + " coins)",
				(xPosition + relativeWidth * 1.05f) * c.getWidth(),
				(yPosition + (relativeHeight * 3 / 4)) * c.getHeight(),
				getTextPaint());
	}
}
