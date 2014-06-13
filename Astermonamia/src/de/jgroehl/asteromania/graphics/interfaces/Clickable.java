package de.jgroehl.asteromania.graphics.interfaces;

import de.jgroehl.asteromania.control.GameHandler;

public interface Clickable {

	boolean isClicked(int x, int y, int screenWidth, int screenHeight);

	void performAction(GameHandler gameHandler);

}
