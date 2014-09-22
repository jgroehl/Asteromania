package de.jgroehl.api.graphics.ui;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.interfaces.EventCallback;

public class SimpleClickableElement extends AbstractClickableElement {

	public SimpleClickableElement(float xPosition, float yPosition,
			float width, float height, EventCallback callback, Context context) {
		super(xPosition, yPosition, width, height, callback, context);
	}

	public SimpleClickableElement(float xPosition, float yPosition,
			int graphicsId, float relativeWidth, EventCallback callback,
			Context context) {
		super(xPosition, yPosition, graphicsId, relativeWidth, callback,
				context);
	}

	@Override
	public void update(BaseGameHandler handler) {
	}

}
