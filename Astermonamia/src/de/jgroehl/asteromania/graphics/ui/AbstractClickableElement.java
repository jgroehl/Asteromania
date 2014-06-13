package de.jgroehl.asteromania.graphics.ui;

import android.graphics.Bitmap;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.interfaces.EventCallback;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.graphics.interfaces.Clickable;

public abstract class AbstractClickableElement extends GraphicsObject implements
		Clickable {

	private final EventCallback callback;

	public AbstractClickableElement(float xPosition, float yPosition, Bitmap graphics,
			EventCallback callback) {
		super(xPosition, yPosition, graphics);
		this.callback = callback;
	}

	@Override
	public boolean isClicked(int x, int y, int screenWidth, int screenHeight) {
		return xPosition * screenWidth - getGraphicsWidth() * screenWidth / 2 < x
				&& xPosition * screenWidth + getGraphicsWidth() * screenWidth
						/ 2 > x
				&& yPosition * screenHeight - getGraphicsHeight()
						* screenHeight / 2 < y
				&& yPosition * screenHeight + getGraphicsHeight()
						* screenHeight / 2 > y;
	}

	@Override
	public void performAction(GameHandler gameHandler) {
		callback.action(gameHandler);
	}

}
