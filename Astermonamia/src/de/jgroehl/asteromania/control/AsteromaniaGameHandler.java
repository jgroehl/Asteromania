package de.jgroehl.asteromania.control;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.graphics.GameObject;
import de.jgroehl.api.graphics.interfaces.Killable;
import de.jgroehl.api.io.FileHandler;
import de.jgroehl.api.utils.SensorHandler;
import de.jgroehl.asteromania.graphics.Explosion;
import de.jgroehl.asteromania.graphics.enemies.BaseEnemy;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;
import de.jgroehl.asteromania.graphics.player.SpaceShip;
import de.jgroehl.asteromania.graphics.starfield.Starfield;
import de.jgroehl.asteromania.graphics.ui.Highscore;
import de.jgroehl.asteromania.graphics.ui.overlay.PlayerInfoDisplay;

public class AsteromaniaGameHandler extends BaseGameHandler
{

	private static final int NEW_LEVEL_SHIELD_SECONDS = 6;

	private final SoundManager soundManager;

	private final SpaceShip player;

	private final PlayerInfo playerInfo;

	private final PlayerInfoDisplay playerInfoDisplay;

	private final LevelHandler levelHandler;

	private final Transition transition;

	private final Highscore highscore;

	private final List<BaseEnemy> enemies = new ArrayList<BaseEnemy>();

	private final List<Hitable> hitableObjects = new ArrayList<Hitable>();

	private Starfield starfield;

	private GoogleApiHandler apiHandler;

	public AsteromaniaGameHandler(GameState state, SoundManager soundManager, Context context, FileHandler fileHandler,
			SensorHandler sensorHandler, Transition transition, Highscore highscore, GoogleApiHandler apiHandler)
	{
		super(state, context);
		this.highscore = highscore;
		this.soundManager = soundManager;
		this.transition = transition;
		this.apiHandler = apiHandler;
		add(transition, GameState.MAIN);
		add(highscore, GameState.HIGHSCORE);
		playerInfo = new PlayerInfo(context, fileHandler, apiHandler);
		player = new SpaceShip(sensorHandler, context, playerInfo);
		levelHandler = new LevelHandler();
		playerInfoDisplay = new PlayerInfoDisplay(context, playerInfo, false);
	}

	@Override
	public void update()
	{
		super.update();
		if (levelHandler.isLevelOver())
		{
			if (playerInfo.getLevel() > 0)
			{
				if (!transition.isInitialized())
				{
					transition.initialize();
					playerInfo.incerementBonusFactor();
					if (playerInfo.getCurrentHighscore() > 0)
						playerInfo.addCoins((int) (playerInfo.getLevel() * 1.5));
					playerInfo.nextLevel(this);
				}
				if (transition.isFinished())
				{
					transition.reset();
					fillLevel();
				}
			}
			else
			{
				playerInfo.nextLevel(this);
				fillLevel();
			}
		}
	}

	private void addKillables(List<Killable> killables)
	{
		for (Killable k : killables)
		{
			if (k instanceof GameObject)
				add((GameObject) k, GameState.MAIN);
		}
	}

	public SoundManager getSoundManager()
	{
		return soundManager;
	}

	public SpaceShip getPlayer()
	{
		return player;
	}

	public PlayerInfo getPlayerInfo()
	{
		return playerInfo;
	}

	public PlayerInfoDisplay getPlayerInfoDisplay()
	{
		return playerInfoDisplay;
	}

	public void gameLost()
	{
		resetGame();
		soundManager.playExplosionSound();
		Explosion.createGameOver(this);
		setGameState(GameState.GAME_OVER);
	}

	public void resetGame()
	{
		enemies.clear();
		levelHandler.killAllEntities(this);
		highscore.addNewHighscore(playerInfo.getCurrentHighscore());
		apiHandler.addToLeaderBoard(playerInfo.getCurrentHighscore());
		starfield.reset();
		playerInfo.reset();
		transition.reset();
	}

	public void setStarfield(Starfield starfield)
	{
		this.starfield = starfield;
	}

	public Starfield getStarfield()
	{
		return starfield;
	}

	public Highscore getHighscore()
	{
		return highscore;
	}

	public GoogleApiHandler getApiHandler()
	{
		return apiHandler;
	}

	public void fillLevel()
	{
		player.addShieldSeconds(NEW_LEVEL_SHIELD_SECONDS);
		addKillables(levelHandler.getLevelObjects(getContext(), playerInfo.getLevel()));
	}

	public BaseEnemy getRandomEnemy()
	{
		if (enemies.size() > 0)
			return enemies.get((int) (Math.random() * enemies.size()));
		else
			return null;
	}

	@Override
	public void add(GameObject gameObject, GameState... states)
	{
		if (gameObject instanceof BaseEnemy)
			enemies.add((BaseEnemy) gameObject);
		if (gameObject instanceof Hitable)
			hitableObjects.add((Hitable) gameObject);

		super.add(gameObject, states);
	}

	@Override
	public void remove(GameObject gameObject)
	{
		enemies.remove(gameObject);
		hitableObjects.remove(gameObject);
		super.remove(gameObject);
	}

	public List<Hitable> getHitableObjects()
	{
		return hitableObjects;
	}
}
