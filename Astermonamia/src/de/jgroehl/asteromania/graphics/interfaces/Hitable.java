package de.jgroehl.asteromania.graphics.interfaces;

import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.game.Shot;

public interface Hitable {

	void getShot(GameHandler gameHandler, Shot shot);

}
