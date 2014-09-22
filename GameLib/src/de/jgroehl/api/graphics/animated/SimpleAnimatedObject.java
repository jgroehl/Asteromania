package de.jgroehl.api.graphics.animated;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;

public class SimpleAnimatedObject extends AnimatedGraphicsObject {

	public SimpleAnimatedObject(float xPosition, float yPosition,
			int imageResource, float relativeWidth, int frameCount,
			int animationPeriod, Context context) {
		super(xPosition, yPosition, imageResource, relativeWidth, frameCount,
				animationPeriod, context);
	}

	@Override
	public void update(BaseGameHandler gameHandler) {
		setFrame((getFrame() + 1) % getMaxFrame());
	}

}
