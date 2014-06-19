package de.jgroehl.asteromania.control;

import android.graphics.BitmapFactory;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.callbacks.MenuButtonCallback;
import de.jgroehl.asteromania.control.callbacks.ShotFiredCallback;
import de.jgroehl.asteromania.control.interfaces.EventCallback;
import de.jgroehl.asteromania.graphics.animated.SimpleAnimatedObject;
import de.jgroehl.asteromania.graphics.game.Enemy;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.graphics.game.SpaceShip;
import de.jgroehl.asteromania.graphics.starfield.Starfield;
import de.jgroehl.asteromania.graphics.ui.Button;
import de.jgroehl.asteromania.sensoryInfo.SensorHandler;

public class GameSetup {

	public static void initializeGameObjects(GameHandler gameHandler,
			SensorHandler sensorHandler) {
		gameHandler.add(new Starfield(), GameState.MAIN, GameState.MENU);
		gameHandler.update();

		gameHandler.add(new Button("Start", 0.3f, 0.07f, 0.4f, 0.2f,
				gameHandler.getResources(), new MenuButtonCallback(
						GameState.MAIN)), GameState.MENU);
		gameHandler.update();
		gameHandler.add(new Button("Score", 0.3f, 0.295f, 0.4f, 0.2f,
				gameHandler.getResources(), new MenuButtonCallback(
						GameState.HIGHSCORE)), GameState.MENU);
		gameHandler.update();
		gameHandler
				.add(new Button("Shop", 0.3f, 0.52f, 0.4f, 0.2f, gameHandler
						.getResources(), new MenuButtonCallback(GameState.SHOP)),
						GameState.MENU);
		gameHandler.update();
		gameHandler.add(new Button("Quit", 0.3f, 0.745f, 0.4f, 0.2f,
				gameHandler.getResources(), new EventCallback() {

					@Override
					public void action(GameHandler gamehandler) {
						System.exit(0);
					}
				}), GameState.MENU);
		gameHandler.update();

		gameHandler.add(
				new SpaceShip(BitmapFactory.decodeResource(
						gameHandler.getResources(), R.drawable.spaceship2),
						sensorHandler, gameHandler.getResources()),
				GameState.MAIN);
		gameHandler.add(
				new Enemy(BitmapFactory.decodeResource(
						gameHandler.getResources(), R.drawable.enemy), 12, 50),
				GameState.MAIN);
		gameHandler.update();

		gameHandler.add(
				new Button("Shoot", 0f, 0.9f, 0.1f, 0.1f, gameHandler
						.getResources(), new ShotFiredCallback(Target.ENEMY)),
				GameState.MAIN);
		gameHandler.add(
				new Button("Shoot", 0.9f, 0.9f, 0.1f, 0.1f, gameHandler
						.getResources(), new ShotFiredCallback(Target.ENEMY)),
				GameState.MAIN);
		gameHandler.update();

		gameHandler.add(
				new Button(BitmapFactory.decodeResource(
						gameHandler.getResources(), R.drawable.settings),
						0.875f, 0f, 0.05f, 0.075f, gameHandler.getResources(),
						new MenuButtonCallback(GameState.SETTINGS)),
				GameState.MAIN);
		gameHandler.update();

		Button button = new Button(BitmapFactory.decodeResource(
				gameHandler.getResources(), R.drawable.home), 0.95f, 0f, 0.05f,
				0.075f, gameHandler.getResources(), new MenuButtonCallback(
						GameState.MENU));
		for (GameState state : GameState.values())
			if (!state.equals(GameState.MENU)) {
				gameHandler.add(button, state);
				gameHandler.update();
			}

		gameHandler.add(
				new SimpleAnimatedObject(0.5f, 0.5f, BitmapFactory
						.decodeResource(gameHandler.getResources(),
								R.drawable.coin), 13, 100), GameState.MAIN);
	}

}
