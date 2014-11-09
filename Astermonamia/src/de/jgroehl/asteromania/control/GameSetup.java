package de.jgroehl.asteromania.control;

import android.graphics.BitmapFactory;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.api.graphics.ui.Button;
import de.jgroehl.api.graphics.ui.SimpleClickableElement;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.callbacks.BuyItemCallback.ItemType;
import de.jgroehl.asteromania.control.callbacks.MenuButtonCallback;
import de.jgroehl.asteromania.control.callbacks.PurchaseItemCallback.PurchaseType;
import de.jgroehl.asteromania.control.callbacks.FromCheckpointCallback;
import de.jgroehl.asteromania.control.callbacks.ShotFiredCallback;
import de.jgroehl.asteromania.control.callbacks.ShowLeaderboardCallback;
import de.jgroehl.asteromania.graphics.starfield.Starfield;
import de.jgroehl.asteromania.graphics.ui.BuyItemShopButton;
import de.jgroehl.asteromania.graphics.ui.overlay.GameOverDisplay;
import de.jgroehl.asteromania.graphics.ui.overlay.NewGameDisplay;
import de.jgroehl.asteromania.graphics.ui.overlay.PlayerInfoDisplay;
import de.jgroehl.asteromania.graphics.ui.overlay.PlayerStatsDisplay;

public class GameSetup {

	private boolean initialized = false;

	public void initializeGameObjects(AsteromaniaGameHandler gameHandler) {
		Starfield starfield = new Starfield(gameHandler.getContext());
		gameHandler.add(starfield, GameState.values());
		gameHandler.setStarfield(starfield);
		gameHandler.update();

		gameHandler.add(new Button(gameHandler.getContext().getResources()
				.getString(de.jgroehl.asteromania.R.string.play), 0.2f, 0.07f,
				0.6f, 0.2f, new MenuButtonCallback(GameState.START),
				gameHandler.getContext()), GameState.MENU);

		gameHandler.add(
				new Button(gameHandler.getContext().getResources()
						.getString(de.jgroehl.asteromania.R.string.checkpoint),
						0.2f, 0.3f, 0.6f, 0.2f, new FromCheckpointCallback(),
						gameHandler.getContext()), GameState.START);

		gameHandler.add(
				new Button(gameHandler.getContext().getResources()
						.getString(de.jgroehl.asteromania.R.string.start),
						0.2f, 0.7f, 0.6f, 0.2f, new MenuButtonCallback(
								GameState.MAIN), gameHandler.getContext()),
				GameState.START);

		gameHandler.add(new NewGameDisplay(gameHandler.getContext(),
				gameHandler.getPlayerInfo()), GameState.START);

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
					public void action(BaseGameHandler gamehandler) {
						if (gamehandler instanceof AsteromaniaGameHandler) {
							((AsteromaniaGameHandler) gamehandler)
									.getPlayerInfo().savePlayerInfo();
							System.exit(0);
						}
					}
				}, gameHandler.getContext()), GameState.MENU);
		gameHandler.update();

		gameHandler.add(new SimpleClickableElement(0.68f, 0.75f,
				R.drawable.shotfield, 0.3f, new ShotFiredCallback(),
				gameHandler.getContext()), GameState.MAIN);
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

		gameHandler.add(
				new Button(BitmapFactory.decodeResource(gameHandler
						.getContext().getResources(), R.drawable.score_icon),
						0.4f, 0.8f, 0.2f, 0.2f, new ShowLeaderboardCallback(),
						gameHandler.getContext()), GameState.HIGHSCORE);

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
		gameHandler.add(
				new Button(BitmapFactory.decodeResource(gameHandler
						.getContext().getResources(), R.drawable.right), 0.93f,
						0.875f, 0.07f, 0.125f, new MenuButtonCallback(
								GameState.SHOP3), gameHandler.getContext()),
				GameState.SHOP2);
		gameHandler.add(
				new Button(BitmapFactory.decodeResource(gameHandler
						.getContext().getResources(), R.drawable.left), 0f,
						0.875f, 0.07f, 0.125f, new MenuButtonCallback(
								GameState.SHOP2), gameHandler.getContext()),
				GameState.SHOP3);
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

		gameHandler.add(new BuyItemShopButton(R.drawable.shield_upgrade, 0.1f,
				0.1f, shopButtonWidth, shopButtonHeight,
				PurchaseType.SHIELD_GENERATOR, gameHandler.getContext(),
				gameHandler.getPlayerInfo()), GameState.SHOP3);

		gameHandler.add(
				new BuyItemShopButton(R.drawable.doubleshot_upgrade, 0.1f,
						0.4f, shopButtonWidth, shopButtonHeight,
						PurchaseType.DOUBLE_SHOT, gameHandler.getContext(),
						gameHandler.getPlayerInfo()), GameState.SHOP3);

		gameHandler.add(
				new BuyItemShopButton(R.drawable.tripleshot_upgrade, 0.1f,
						0.7f, shopButtonWidth, shopButtonHeight,
						PurchaseType.TRIPLE_SHOT, gameHandler.getContext(),
						gameHandler.getPlayerInfo()), GameState.SHOP3);

		gameHandler.update();

		gameHandler.add(new PlayerInfoDisplay(gameHandler.getContext(),
				gameHandler.getPlayerInfo(), true), GameState.SHOP,
				GameState.SHOP2, GameState.SHOP3);

		gameHandler.add(new Button(gameHandler.getContext().getResources()
				.getString(de.jgroehl.asteromania.R.string.back), 0.4f, 0.75f,
				0.5f, 0.2f, new EventCallback() {

					@Override
					public void action(BaseGameHandler gameHandler) {
						if (gameHandler instanceof AsteromaniaGameHandler) {
							((AsteromaniaGameHandler) gameHandler)
									.getPlayerInfo().resetLastHighscore();
							gameHandler.setState(GameState.MENU);
						}
					}
				}, gameHandler.getContext()), GameState.GAME_OVER);

		gameHandler.add(
				new Button(BitmapFactory.decodeResource(gameHandler
						.getContext().getResources(), R.drawable.score_icon),
						0.1f, 0.75f, 0.2f, 0.2f, new ShowLeaderboardCallback(),
						gameHandler.getContext()), GameState.GAME_OVER);

		gameHandler.add(new GameOverDisplay(gameHandler.getContext(),
				gameHandler.getPlayerInfo(), gameHandler.getHighscore()),
				GameState.GAME_OVER);
		gameHandler.update();

		gameHandler.add(new PlayerStatsDisplay(gameHandler.getPlayerInfo(),
				gameHandler.getContext()), GameState.STATS);
		gameHandler.update();
		initialized = true;

	}

	public boolean alreadyInitialized() {
		return initialized;
	}
}
