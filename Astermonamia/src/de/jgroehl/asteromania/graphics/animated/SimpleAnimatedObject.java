package de.jgroehl.asteromania.graphics.animated;

import android.content.Context;
import de.jgroehl.asteromania.control.GameHandler;

public class SimpleAnimatedObject extends AnimatedGraphicsObject {

	public SimpleAnimatedObject(float xPosition, float yPosition,
			int imageResource, float relativeWidth, int frameCount,
			int animationPeriod, Context context) {
		super(xPosition, yPosition, imageResource, relativeWidth, frameCount,
				animationPeriod, context);
	}

	@Override
	public void update(GameHandler gameHandler) {
		setFrame((getFrame() + 1) % getMaxFrame());
	}

}
