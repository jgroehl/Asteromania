package de.jgroehl.asteromania.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.MotionEvent;
import de.jgroehl.asteromania.control.interfaces.EventHandler;
import de.jgroehl.asteromania.control.interfaces.GraphicsHandler;
import de.jgroehl.asteromania.graphics.GameObject;
import de.jgroehl.asteromania.graphics.GameObject.Level;
import de.jgroehl.asteromania.graphics.game.Explosion;
import de.jgroehl.asteromania.graphics.game.SpaceShip;
import de.jgroehl.asteromania.graphics.interfaces.Clickable;
import de.jgroehl.asteromania.graphics.interfaces.Drawable;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;
import de.jgroehl.asteromania.graphics.interfaces.Killable;
import de.jgroehl.asteromania.graphics.interfaces.Updatable;
import de.jgroehl.asteromania.graphics.starfield.Starfield;
import de.jgroehl.asteromania.io.FileHandler;
import de.jgroehl.asteromania.player.PlayerInfo;
import de.jgroehl.asteromania.player.PlayerInfoDisplay;
import de.jgroehl.asteromania.sensoryInfo.SensorHandler;

public class GameHandler implements GraphicsHandler, EventHandler {

	private final Map<GameState, List<GameObject>> gameObjects = new HashMap<GameState, List<GameObject>>();

	private final Map<GameState, List<Clickable>> clickableObjects = new HashMap<GameState, List<Clickable>>();

	private final Map<GameObject, GameState[]> addedObjects = new HashMap<GameObject, GameState[]>();

	private final Map<GameState, List<Hitable>> hitableObjects = new HashMap<GameState, List<Hitable>>();

	private final List<GameObject> removedObjects = new ArrayList<GameObject>();

	private final SoundManager soundManager;

	private final SpaceShip player;

	private final Context context;

	private final PlayerInfo playerInfo;

	private final PlayerInfoDisplay playerInfoDisplay;

	private final LevelHandler levelHandler;

	private final Transition transition;

	private GameState state = null;

	private Starfield starfield;

	public GameHandler(GameState state, SoundManager soundManager,
			Context context, FileHandler fileHandler,
			SensorHandler sensorHandler, Transition transition) {
		this.state = state;
		for (GameState s : GameState.values()) {
			gameObjects.put(s, new ArrayList<GameObject>());
			clickableObjects.put(s, new ArrayList<Clickable>());
			hitableObjects.put(s, new ArrayList<Hitable>());
		}
		this.soundManager = soundManager;
		this.context = context;
		this.transition = transition;
		add(transition, GameState.MAIN);
		player = new SpaceShip(sensorHandler, context);
		playerInfo = new PlayerInfo(context, fileHandler);
		levelHandler = new LevelHandler();
		playerInfoDisplay = new PlayerInfoDisplay(context, playerInfo, false);
	}

	public void update() {
		if (addedObjects.size() > 0) {
			for (GameObject gameObject : addedObjects.keySet()) {
				for (GameState state : addedObjects.get(gameObject))
					if (gameObject.getLevel().equals(Level.TOP))
						gameObjects.get(state).add(gameObject);
					else
						gameObjects.get(state).add(0, gameObject);

				if (gameObject instanceof Clickable) {
					for (GameState state : addedObjects.get(gameObject))
						clickableObjects.get(state).add((Clickable) gameObject);
				}

				if (gameObject instanceof Hitable) {
					for (GameState state : addedObjects.get(gameObject))
						hitableObjects.get(state).add((Hitable) gameObject);
				}

				if (gameObject instanceof Starfield) {
					starfield = (Starfield) gameObject;
				}
			}
			addedObjects.clear();
		}

		if (removedObjects.size() > 0) {
			for (GameObject gameObject : removedObjects) {
				for (List<GameObject> l : gameObjects.values()) {
					if (l.contains(gameObject))
						l.remove(gameObject);
				}
				for (List<Clickable> l : clickableObjects.values()) {
					if (l.contains(gameObject))
						l.remove(gameObject);
				}
				for (List<Hitable> l : hitableObjects.values()) {
					if (l.contains(gameObject))
						l.remove(gameObject);
				}
			}
			removedObjects.clear();
		}

		if (levelHandler.isLevelOver()) {
			if (playerInfo.getLevel() > 0) {
				if (!transition.isInitialized()) {
					transition.initialize();
					playerInfo.addCoins(playerInfo.getLevel());
				}
				if (transition.isFinished()) {
					transition.reset();
					addKillables(levelHandler.getLevelObjects(context,
							playerInfo.nextLevel()));
				}
			} else {
				addKillables(levelHandler.getLevelObjects(context,
						playerInfo.nextLevel()));
			}
		}
	}

	@Override
	public void add(GameObject gameObject, GameState... states) {
		addedObjects.put(gameObject, states);
	}

	@Override
	public void remove(GameObject gameObject) {
		removedObjects.add(gameObject);
	}

	@Override
	public List<? extends Drawable> getAllDrawableObjects() {
		return gameObjects.get(state);
	}

	@Override
	public List<? extends Updatable> getAllUpdatableObjects() {
		return gameObjects.get(state);
	}

	@Override
	public void handleEvent(MotionEvent event, Context context,
			int screenWidth, int screenHeight) {
		for (Clickable c : clickableObjects.get(state)) {
			if (c.isClicked((int) event.getX(), (int) event.getY(),
					screenWidth, screenHeight)) {
				c.performAction(this);
			}
		}

	}

	public void addAllToMain(List<GameObject> gameObjects) {
		for (GameObject gameObject : gameObjects)
			add(gameObject, GameState.MAIN);
	}

	private void addKillables(List<Killable> killables) {
		for (Killable k : killables) {
			if (k instanceof GameObject)
				add((GameObject) k, GameState.MAIN);
		}
	}

	public GameState getGameState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public SpaceShip getPlayer() {
		return player;
	}

	public Context getContext() {
		return context;
	}

	public List<Hitable> getHitableObjects() {
		return hitableObjects.get(state);
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public PlayerInfoDisplay getPlayerInfoDisplay() {
		return playerInfoDisplay;
	}

	public void gameLost() {
		levelHandler.killAllEntities(this);
		playerInfo.resetHealth();
		playerInfo.resetLevel();
		soundManager.playExplosionSound();
		add(new Explosion(0.45f, 0.45f, context), GameState.GAME_OVER);
		state = GameState.GAME_OVER;
	}

	public Starfield getStarfield() {
		return starfield;
	}

}
