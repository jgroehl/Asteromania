package de.jgroehl.asteromania.graphics.ui;

import android.graphics.Bitmap;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.interfaces.EventCallback;

public class SimpleClickableElement extends AbstractClickableElement {

	public SimpleClickableElement(float xPosition, float yPosition,
			Bitmap graphics, EventCallback callback, Align align) {
		super(xPosition, yPosition, graphics, callback, align);
	}

	@Override
	public void update(GameHandler handler) {
	}

}
