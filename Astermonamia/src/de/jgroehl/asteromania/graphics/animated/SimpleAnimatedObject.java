package de.jgroehl.asteromania.graphics.animated;

import de.jgroehl.asteromania.control.GameHandler;
import android.graphics.Bitmap;

public class SimpleAnimatedObject extends AnimatedGraphicsObject {

	public SimpleAnimatedObject(float xPosition, float yPosition,
			Bitmap graphics, int frameCount, int animationPeriod, Align align) {
		super(xPosition, yPosition, graphics, frameCount, animationPeriod, align);

	}

	@Override
	public void update(GameHandler gameHandler) {
		setFrame((getFrame() + 1) % getMaxFrame());
	}

}
