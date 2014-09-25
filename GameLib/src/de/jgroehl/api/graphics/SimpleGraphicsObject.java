package de.jgroehl.api.graphics;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;

public class SimpleGraphicsObject extends GraphicsObject {

	public SimpleGraphicsObject(float xPosition, float yPosition, float width,
			float height, Context context) {
		super(xPosition, yPosition, width, height, context);
	}

	public SimpleGraphicsObject(float xPosition, float yPosition,
			int graphicsId, float width, Context context) {
		super(xPosition, yPosition, graphicsId, width, context);
	}

	@Override
	public void update(BaseGameHandler gameHandler) {
		// Nothing to do here
	}

}
