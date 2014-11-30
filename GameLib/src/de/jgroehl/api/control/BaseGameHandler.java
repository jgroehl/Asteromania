package de.jgroehl.api.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import de.jgroehl.api.AbstractGamePanel;
import de.jgroehl.api.control.interfaces.EventHandler;
import de.jgroehl.api.control.interfaces.GraphicsHandler;
import de.jgroehl.api.graphics.GameObject;
import de.jgroehl.api.graphics.GameObject.Level;
import de.jgroehl.api.graphics.interfaces.Clickable;
import de.jgroehl.api.graphics.interfaces.Drawable;
import de.jgroehl.api.graphics.interfaces.Hitable;
import de.jgroehl.api.graphics.interfaces.Updatable;

public class BaseGameHandler implements GraphicsHandler, EventHandler
{

	private static final String TAG = BaseGameHandler.class.getSimpleName();

	private final Map<GameState, List<GameObject>> gameObjects = new HashMap<GameState, List<GameObject>>();

	private final Map<GameState, List<Clickable>> clickableObjects = new HashMap<GameState, List<Clickable>>();

	private final Map<GameState, List<Hitable>> hitableObjects = new HashMap<GameState, List<Hitable>>();

	private final Map<GameObject, GameState[]> addedObjects = new HashMap<GameObject, GameState[]>();

	private final List<GameObject> removedObjects = new ArrayList<GameObject>();

	private GameState state = null;

	private final Context context;

	private final AbstractGamePanel gamePanel;

	public BaseGameHandler(GameState state, Context context, AbstractGamePanel gamePanel)
	{
		this.context = context;
		this.state = state;
		this.gamePanel = gamePanel;
		for (GameState s : GameState.values())
		{
			gameObjects.put(s, new ArrayList<GameObject>());
			clickableObjects.put(s, new ArrayList<Clickable>());
			hitableObjects.put(s, new ArrayList<Hitable>());
		}
	}

	public List<Hitable> getHitableObjects()
	{
		return hitableObjects.get(state);
	}

	public GameState getGameState()
	{
		return state;
	}

	public void setState(GameState state)
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

				if (gameObject instanceof Hitable)
				{
					for (GameState state : addedObjects.get(gameObject))
						hitableObjects.get(state).add((Hitable) gameObject);
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
				for (List<Hitable> l : hitableObjects.values())
				{
					if (l.contains(gameObject))
						l.remove(gameObject);
				}
			}
			removedObjects.clear();
		}
	}

	@Override
	public void add(GameObject gameObject, GameState... states)
	{
		if (gameObject != null)
			addedObjects.put(gameObject, states);
	}

	@Override
	public void remove(GameObject gameObject)
	{
		removedObjects.add(gameObject);
	}

	@Override
	public List<? extends Drawable> getAllDrawableObjects()
	{
		return gameObjects.get(state);
	}

	@Override
	public List<? extends Updatable> getAllUpdatableObjects()
	{
		return gameObjects.get(state);
	}

	@Override
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

	public AbstractGamePanel getGamePanel()
	{
		return gamePanel;
	}

	public Context getContext()
	{
		return context;
	}

	public boolean gameObjectsInitialized()
	{
		Log.d(TAG, "Game Objects size: " + totalSize(gameObjects));
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
