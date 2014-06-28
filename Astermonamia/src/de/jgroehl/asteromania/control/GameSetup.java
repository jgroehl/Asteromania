package de.jgroehl.asteromania.control;

import android.graphics.BitmapFactory;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.callbacks.MenuButtonCallback;
import de.jgroehl.asteromania.control.callbacks.ShotFiredCallback;
import de.jgroehl.asteromania.control.interfaces.EventCallback;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.graphics.starfield.Starfield;
import de.jgroehl.asteromania.graphics.ui.Button;
import de.jgroehl.asteromania.graphics.ui.SimpleClickableElement;

public class GameSetup {

	public void initializeGameObjects(GameHandler gameHandler) {
		gameHandler.add(new Starfield(gameHandler.getContext()),
				GameState.MAIN, GameState.MENU);
		gameHandler.update();

		gameHandler.add(
				new Button("Start", 0.3f, 0.07f, 0.4f, 0.2f,
						new MenuButtonCallback(GameState.MAIN), gameHandler
								.getContext()), GameState.MENU);
		gameHandler.update();
		gameHandler.add(
				new Button("Score", 0.3f, 0.295f, 0.4f, 0.2f,
						new MenuButtonCallback(GameState.HIGHSCORE),
						gameHandler.getContext()), GameState.MENU);
		gameHandler.update();
		gameHandler.add(
				new Button("Shop", 0.3f, 0.52f, 0.4f, 0.2f,
						new MenuButtonCallback(GameState.SHOP), gameHandler
								.getContext()), GameState.MENU);
		gameHandler.update();
		gameHandler.add(new Button("Quit", 0.3f, 0.745f, 0.4f, 0.2f,
				new EventCallback() {

					@Override
					public void action(GameHandler gamehandler) {
						gamehandler.getPlayerInfo().savePlayerInfo();
						System.exit(0);
					}
				}, gameHandler.getContext()), GameState.MENU);
		gameHandler.update();

		gameHandler.add(new SimpleClickableElement(0.8f, 0.8f, 0.2f, 0.2f,
				new ShotFiredCallback(Target.ENEMY), gameHandler.getContext()),
				GameState.MAIN);
		gameHandler.update();

		gameHandler.add(
				new Button(BitmapFactory.decodeResource(gameHandler
						.getContext().getResources(), R.drawable.settings),
						0.875f, 0f, 0.05f, 0.075f, new MenuButtonCallback(
								GameState.SETTINGS), gameHandler.getContext()),
				GameState.MAIN);
		gameHandler.update();

		Button button = new Button(BitmapFactory.decodeResource(gameHandler
				.getContext().getResources(), R.drawable.home), 0.95f, 0f,
				0.05f, 0.075f, new MenuButtonCallback(GameState.MENU),
				gameHandler.getContext());
		for (GameState state : GameState.values())
			if (!state.equals(GameState.MENU)) {
				gameHandler.add(button, state);
				gameHandler.update();
			}

		gameHandler.add(gameHandler.getPlayer(), GameState.MAIN);
		gameHandler.update();
	}
}
