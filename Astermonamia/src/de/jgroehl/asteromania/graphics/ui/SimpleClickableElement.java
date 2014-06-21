package de.jgroehl.asteromania.graphics.ui;

import android.content.Context;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.interfaces.EventCallback;

public class SimpleClickableElement extends AbstractClickableElement {

	public SimpleClickableElement(float xPosition, float yPosition,
			int graphicsId, EventCallback callback, Context context) {
		super(xPosition, yPosition, graphicsId, callback, context);
	}

	@Override
	public void update(GameHandler handler) {
	}

}
