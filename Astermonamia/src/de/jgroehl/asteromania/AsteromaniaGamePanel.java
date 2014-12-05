package de.jgroehl.asteromania;

import android.content.Context;
import android.view.SurfaceHolder;
import de.jgroehl.api.AbstractGamePanel;
import de.jgroehl.api.activities.AbstractGooglePlayGamesLoginActivity;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.crypto.CryptoHandler;
import de.jgroehl.api.io.FileHandler;
import de.jgroehl.api.utils.SensorHandler;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.control.GameSetup;
import de.jgroehl.asteromania.control.GoogleApiHandler;
import de.jgroehl.asteromania.control.SoundManager;
import de.jgroehl.asteromania.control.Transition;
import de.jgroehl.asteromania.graphics.ui.Highscore;

public class AsteromaniaGamePanel extends AbstractGamePanel
{

	private GameSetup gameSetup = new GameSetup();

	public AsteromaniaGamePanel(Context context, GoogleApiHandler handler,
			AbstractGooglePlayGamesLoginActivity abstractMainActivity)
	{

		super(context, abstractMainActivity, new AsteromaniaGameHandler(GameState.MENU, new SoundManager(context),
				context, new FileHandler(new CryptoHandler(context), context), new SensorHandler(context,
						Context.SENSOR_SERVICE), new Transition(context), new Highscore(context, new FileHandler(
						new CryptoHandler(context), context)), handler));

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		super.surfaceDestroyed(holder);
		getGameHandler().getPlayerInfo().savePlayerInfo();
	}

	public AsteromaniaGameHandler getGameHandler()
	{
		return (AsteromaniaGameHandler) getBaseGameHandler();
	}

	@Override
	public void initializeGameObjects()
	{
		gameSetup.initializeGameObjects(getGameHandler());
	}
}
