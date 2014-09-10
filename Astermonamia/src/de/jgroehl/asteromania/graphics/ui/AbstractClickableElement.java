package de.jgroehl.asteromania.graphics.ui;

import android.content.Context;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.interfaces.EventCallback;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.graphics.interfaces.Clickable;

public abstract class AbstractClickableElement extends GraphicsObject implements
		Clickable {

	private final EventCallback callback;

	public AbstractClickableElement(float xPosition, float yPosition,
			float width, float height, EventCallback callback, Context context) {
		super(xPosition, yPosition, width, height, context);
		this.callback = callback;
	}

	public AbstractClickableElement(float xPosition, float yPosition,
			int graphicsId, float relativeWidth, EventCallback callback, Context context) {
		super(xPosition, yPosition, graphicsId, relativeWidth, context);
		this.callback = callback;
	}

	@Override
	public boolean isClicked(int x, int y, int screenWidth, int screenHeight) {
		return xPosition * screenWidth < x
				&& xPosition * screenWidth + getRelativeWidth() * screenWidth > x
				&& yPosition * screenHeight < y
				&& yPosition * screenHeight + getRelativeHeight()
						* screenHeight > y;
	}

	@Override
	public void performAction(GameHandler gameHandler) {
		callback.action(gameHandler);
	}

	protected EventCallback getCallback() {
		return callback;
	}

}
