package de.jgroehl.asteromania.graphics.ui;

import android.content.Context;
import android.graphics.Bitmap;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.interfaces.EventCallback;

public class SimpleClickableElement extends AbstractClickableElement {

	public SimpleClickableElement(float xPosition, float yPosition,
			Bitmap graphics, EventCallback callback, Context context) {
		super(xPosition, yPosition, graphics, callback, context);
	}

	@Override
	public void update(GameHandler handler) {
	}

}
