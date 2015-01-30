package de.jgroehl.asteromania.graphics.interfaces;

import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.AbstractDamagingAbility;

public interface Hitable {

	void getShot(BaseGameHandler gameHandler, AbstractDamagingAbility shot);

	boolean isAlive();

}
