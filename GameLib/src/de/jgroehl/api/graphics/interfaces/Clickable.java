package de.jgroehl.api.graphics.interfaces;

import de.jgroehl.api.control.BaseGameHandler;

public interface Clickable {

	boolean isClicked(int x, int y, int screenWidth, int screenHeight);

	void performAction(BaseGameHandler gameHandler);

}
