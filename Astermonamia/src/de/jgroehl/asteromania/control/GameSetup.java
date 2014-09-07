package de.jgroehl.asteromania.control;

import android.graphics.BitmapFactory;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.callbacks.BuyItemCallback.ItemType;
import de.jgroehl.asteromania.control.callbacks.MenuButtonCallback;
import de.jgroehl.asteromania.control.callbacks.ShotFiredCallback;
import de.jgroehl.asteromania.control.interfaces.EventCallback;
import de.jgroehl.asteromania.graphics.game.player.PlayerInfoDisplay;
import de.jgroehl.asteromania.graphics.game.player.PlayerStatsDisplay;
import de.jgroehl.asteromania.graphics.starfield.GameOverDisplay;
import de.jgroehl.asteromania.graphics.starfield.Starfield;
import de.jgroehl.asteromania.graphics.ui.Button;
import de.jgroehl.asteromania.graphics.ui.BuyItemShopButton;
import de.jgroehl.asteromania.graphics.ui.SimpleClickableElement;

public class GameSetup {

	public void initializeGameObjects(GameHandler gameHandler) {
		gameHandler.add(new Starfield(gameHandler.getContext()),
				GameState.values());
		gameHandler.update();

		gameHandler.add(
				new Button(gameHandler.getContext().getResources()
						.getString(de.jgroehl.asteromania.R.string.start),
						0.2f, 0.07f, 0.6f, 0.2f, new MenuButtonCallback(
								GameState.MAIN), gameHandler.getContext()),
				GameState.MENU);
		gameHandler.update();
		gameHandler
				.add(new Button(gameHandler.getContext().getResources()
						.getString(de.jgroehl.asteromania.R.string.score),
						0.2f, 0.295f, 0.29f, 0.2f, new MenuButtonCallback(
								GameState.HIGHSCORE), gameHandler.getContext()),
						GameState.MENU);
		gameHandler.update();
		gameHandler.add(new Button(gameHandler.getContext().getResources()
				.getString(de.jgroehl.asteromania.R.string.statistics), 0.51f,
				0.295f, 0.29f, 0.2f, new MenuButtonCallback(GameState.STATS),
				gameHandler.getContext()), GameState.MENU);
		gameHandler.update();
		gameHandler.add(
				new Button(gameHandler.getContext().getResources()
						.getString(de.jgroehl.asteromania.R.string.shop), 0.2f,
						0.52f, 0.6f, 0.2f, new MenuButtonCallback(
								GameState.SHOP), gameHandler.getContext()),
				GameState.MENU);
		gameHandler.update();
		gameHandler.add(new Button(gameHandler.getContext().getResources()
				.getString(de.jgroehl.asteromania.R.string.quit), 0.2f, 0.745f,
				0.6f, 0.2f, new EventCallback() {

					@Override
					public void action(GameHandler gamehandler) {
						gamehandler.getPlayerInfo().savePlayerInfo();
						System.exit(0);
					}
				}, gameHandler.getContext()), GameState.MENU);
		gameHandler.update();

		gameHandler.add(new SimpleClickableElement(0.7f, 0.7f, 0.3f, 0.3f,
				new ShotFiredCallback(), gameHandler.getContext()),
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

		gameHandler.add(
				new Button(BitmapFactory.decodeResource(gameHandler
						.getContext().getResources(), R.drawable.right), 0.93f,
						0.875f, 0.07f, 0.125f, new MenuButtonCallback(
								GameState.SHOP2), gameHandler.getContext()),
				GameState.SHOP);
		gameHandler.add(
				new Button(BitmapFactory.decodeResource(gameHandler
						.getContext().getResources(), R.drawable.left), 0f,
						0.875f, 0.07f, 0.125f, new MenuButtonCallback(
								GameState.SHOP), gameHandler.getContext()),
				GameState.SHOP2);
		gameHandler.update();

		float shopButtonWidth = 0.15f;
		float shopButtonHeight = 0.2f;

		gameHandler.add(
				new BuyItemShopButton(
						de.jgroehl.asteromania.R.drawable.life_upgrade, 0.1f,
						0.1f, shopButtonWidth, shopButtonHeight, ItemType.HP,
						gameHandler.getContext(), gameHandler.getPlayerInfo()),
				GameState.SHOP);

		gameHandler.add(
				new BuyItemShopButton(
						de.jgroehl.asteromania.R.drawable.damage_upgrade, 0.1f,
						0.4f, shopButtonWidth, shopButtonHeight,
						ItemType.DAMAGE, gameHandler.getContext(), gameHandler
								.getPlayerInfo()), GameState.SHOP);

		gameHandler.add(
				new BuyItemShopButton(
						de.jgroehl.asteromania.R.drawable.speed_upgrade, 0.1f,
						0.7f, shopButtonWidth, shopButtonHeight,
						ItemType.SPEED, gameHandler.getContext(), gameHandler
								.getPlayerInfo()), GameState.SHOP);

		gameHandler.add(new BuyItemShopButton(
				de.jgroehl.asteromania.R.drawable.shotspeed_upgrade, 0.1f,
				0.1f, shopButtonWidth, shopButtonHeight, ItemType.SHOT_SPEED,
				gameHandler.getContext(), gameHandler.getPlayerInfo()),
				GameState.SHOP2);

		gameHandler.add(new BuyItemShopButton(
				de.jgroehl.asteromania.R.drawable.shotfreq_upgrade, 0.1f, 0.4f,
				shopButtonWidth, shopButtonHeight, ItemType.SHOT_FREQUENCY,
				gameHandler.getContext(), gameHandler.getPlayerInfo()),
				GameState.SHOP2);

		gameHandler.add(new BuyItemShopButton(
				de.jgroehl.asteromania.R.drawable.shield_upgrade, 0.1f, 0.7f,
				shopButtonWidth, shopButtonHeight, ItemType.SHIELD_SECONDS,
				gameHandler.getContext(), gameHandler.getPlayerInfo()),
				GameState.SHOP2);

		gameHandler.update();

		gameHandler.add(new PlayerInfoDisplay(gameHandler.getContext(),
				gameHandler.getPlayerInfo(), true), GameState.SHOP);

		gameHandler.add(new PlayerInfoDisplay(gameHandler.getContext(),
				gameHandler.getPlayerInfo(), true), GameState.SHOP2);

		gameHandler.add(new Button(gameHandler.getContext().getResources()
				.getString(de.jgroehl.asteromania.R.string.back), 0.3f, 0.745f,
				0.4f, 0.2f, new EventCallback() {

					@Override
					public void action(GameHandler gameHandler) {
						gameHandler.getPlayerInfo().resetLastHighscore();
						gameHandler.setState(GameState.MENU);
					}
				}, gameHandler.getContext()), GameState.GAME_OVER);

		gameHandler.add(new GameOverDisplay(gameHandler.getContext(),
				gameHandler.getPlayerInfo(), gameHandler.getHighscore()),
				GameState.GAME_OVER);
		gameHandler.update();

		gameHandler.add(new PlayerStatsDisplay(gameHandler.getPlayerInfo(),
				gameHandler.getContext()), GameState.STATS);
		gameHandler.update();

	}
}
