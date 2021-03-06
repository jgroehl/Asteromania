package de.jgroehl.api.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.MotionEvent;
import de.jgroehl.api.graphics.GameObject;
import de.jgroehl.api.graphics.GameObject.Level;
import de.jgroehl.api.graphics.interfaces.Clickable;
import de.jgroehl.api.graphics.interfaces.Drawable;
import de.jgroehl.api.graphics.interfaces.Updatable;

public class BaseGameHandler
{
	private final Map<GameState, List<GameObject>> gameObjects = new HashMap<GameState, List<GameObject>>();

	private final Map<GameState, List<Clickable>> clickableObjects = new HashMap<GameState, List<Clickable>>();

	private final Map<GameObject, GameState[]> addedObjects = new HashMap<GameObject, GameState[]>();

	private final List<GameObject> removedObjects = new ArrayList<GameObject>();

	private GameState state = null;

	private final Context context;

	public BaseGameHandler(GameState state, Context context)
	{
		this.context = context;
		this.state = state;
		for (GameState s : GameState.values())
		{
			gameObjects.put(s, new ArrayList<GameObject>());
			clickableObjects.put(s, new ArrayList<Clickable>());
		}
	}

	public GameState getGameState()
	{
		return state;
	}

	public void setGameState(GameState state)
	{
		this.state = state;
	}

	public void update()
	{
		if (addedObjects.size() > 0)
		{
			for (GameObject gameObject : addedObjects.keySet())
			{
				for (GameState state : addedObjects.get(gameObject))
					if (gameObject.getLevel().equals(Level.TOP))
						gameObjects.get(state).add(gameObject);
					else
						gameObjects.get(state).add(0, gameObject);

				if (gameObject instanceof Clickable)
				{
					for (GameState state : addedObjects.get(gameObject))
						clickableObjects.get(state).add((Clickable) gameObject);
				}
			}
			addedObjects.clear();
		}

		if (removedObjects.size() > 0)
		{
			for (GameObject gameObject : removedObjects)
			{
				for (List<GameObject> l : gameObjects.values())
				{
					if (l.contains(gameObject))
						l.remove(gameObject);
				}
				for (List<Clickable> l : clickableObjects.values())
				{
					if (l.contains(gameObject))
						l.remove(gameObject);
				}
			}
			removedObjects.clear();
		}
	}

	public void add(GameObject gameObject, GameState... states)
	{
		if (gameObject != null)
			addedObjects.put(gameObject, states);
	}

	public void remove(GameObject gameObject)
	{
		removedObjects.add(gameObject);
	}

	public List<? extends Drawable> getAllDrawableObjects()
	{
		return gameObjects.get(state);
	}

	public List<? extends Updatable> getAllUpdatableObjects()
	{
		return gameObjects.get(state);
	}

	public void handleEvent(MotionEvent event, Context context, int screenWidth, int screenHeight)
	{
		for (Clickable c : clickableObjects.get(state))
		{
			if (c.isClicked((int) event.getX(), (int) event.getY(), screenWidth, screenHeight))
			{
				c.performAction(this);
			}
		}
	}

	public Context getContext()
	{
		return context;
	}

	public boolean gameObjectsInitialized()
	{
		return totalSize(gameObjects) > 0;
	}

	private int totalSize(Map<GameState, List<GameObject>> gameObjects)
	{
		int sum = 0;
		for (List<?> l : gameObjects.values())
		{
			sum += l.size();
		}
		return sum;
	}

}
