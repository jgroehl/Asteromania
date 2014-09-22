package de.jgroehl.api.control.interfaces;

import java.util.List;

import de.jgroehl.api.control.GameState;
import de.jgroehl.api.graphics.GameObject;
import de.jgroehl.api.graphics.interfaces.Drawable;
import de.jgroehl.api.graphics.interfaces.Updatable;

public interface GraphicsHandler {

	void add(GameObject gameObject, GameState... states);

	void remove(GameObject gameObject);

	List<? extends Drawable> getAllDrawableObjects();

	List<? extends Updatable> getAllUpdatableObjects();

}
