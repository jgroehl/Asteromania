package de.jgroehl.asteromania.control.interfaces;

import java.util.List;

import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.graphics.GameObject;
import de.jgroehl.asteromania.graphics.interfaces.Drawable;
import de.jgroehl.asteromania.graphics.interfaces.Updatable;

public interface GraphicsHandler {

	void add(GameObject gameObject, GameState... states);

	void remove(GameObject gameObject);

	List<? extends Drawable> getAllDrawableObjects();

	List<? extends Updatable> getAllUpdatableObjects();

}
