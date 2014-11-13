package de.jgroehl.asteromania.control;

import java.util.List;

import android.content.Context;
import de.jgroehl.api.AbstractGamePanel;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.graphics.GameObject;
import de.jgroehl.api.graphics.interfaces.Killable;
import de.jgroehl.api.io.FileHandler;
import de.jgroehl.api.user.FriendCodeHandler;
import de.jgroehl.api.utils.SensorHandler;
import de.jgroehl.asteromania.graphics.Explosion;
import de.jgroehl.asteromania.graphics.player.SpaceShip;
import de.jgroehl.asteromania.graphics.starfield.Starfield;
import de.jgroehl.asteromania.graphics.ui.Highscore;
import de.jgroehl.asteromania.graphics.ui.overlay.PlayerInfoDisplay;

public class AsteromaniaGameHandler extends BaseGameHandler
{

	private static final int NEW_LEVEL_SHIELD_SECONDS = 4;

	private final SoundManager soundManager;

	private final SpaceShip player;

	private final PlayerInfo playerInfo;

	private final PlayerInfoDisplay playerInfoDisplay;

	private final LevelHandler levelHandler;

	private final Transition transition;

	private final Highscore highscore;

	private final FriendCodeHandler friendCodeHandler;

	private Starfield starfield;

	private GoogleApiHandler apiHandler;

	public AsteromaniaGameHandler(GameState state, SoundManager soundManager, Context context, FileHandler fileHandler,
			SensorHandler sensorHandler, Transition transition, Highscore highscore, GoogleApiHandler apiHandler,
			AbstractGamePanel gamePanel, FriendCodeHandler friendCodeHandler)
	{
		super(state, context, gamePanel);
		this.highscore = highscore;
		this.soundManager = soundManager;
		this.transition = transition;
		this.apiHandler = apiHandler;
		this.friendCodeHandler = friendCodeHandler;
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
					playerInfo.addCoins(playerInfo.getLevel());
					playerInfo.nextLevel();
				}
				if (transition.isFinished())
				{
					transition.reset();
					fillLevel();
				}
				playerInfo.checkForCheckpoint();
			}
			else
			{
				playerInfo.nextLevel();
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
		setState(GameState.GAME_OVER);
	}

	public void resetGame()
	{
		levelHandler.killAllEntities(this);
		highscore.addNewHighscore(playerInfo.getCurrentHighscore());
		apiHandler.addToLeaderBoard(playerInfo.getCurrentHighscore());
		starfield.reset();
		playerInfo.reset();
		// TODO: Fix this!
		transition.setFinished();
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

	public FriendCodeHandler getFriendCodeHandler()
	{
		return friendCodeHandler;
	}

}
