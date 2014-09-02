package de.jgroehl.asteromania.control.callbacks;

import android.widget.Toast;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.interfaces.EventCallback;
import de.jgroehl.asteromania.player.PlayerInfo;

public class BuyItemCallback implements EventCallback {

	public static final int DEFAULT_COST = 1000;

	public enum ItemType {

		HP(1, 2, "Lebenspunkt"), DAMAGE(1, 3, "Schaden"), SPEED(0.1f, 1,
				"Geschwindigkeit"), SHOT_SPEED(0.1f, 2, "Schussgeschwindigkeit"), SHOT_FREQUENCY(
				0.1f, 2, "Schussfrequenz");

		public final float increaseValue;
		private final int baseCost;
		private final String text;

		private ItemType(float increaseValue, int baseCost, String text) {
			this.increaseValue = increaseValue;
			this.baseCost = baseCost;
			this.text = text;
		}

		public int getCost(PlayerInfo playerInfo) {
			switch (this) {
			case HP:
				return (int) (playerInfo.getHealthPoints().getMaximum() * baseCost);
			case DAMAGE:
				return (playerInfo.getBonusDamage() + baseCost)
						* (playerInfo.getBonusDamage() + baseCost);
			case SPEED:
				return (int) Math
						.pow((((playerInfo.getMaxSpeedFactor() - 1) * 6) + baseCost),
								3);
			case SHOT_FREQUENCY:
				return (int) Math
						.pow((((playerInfo.getShotFrequencyFactor() - 1) * 12) + baseCost),
								3);
			case SHOT_SPEED:
				return (int) Math
						.pow((((playerInfo.getShotSpeedFactor() - 1) * 9) + baseCost),
								3);
			default:
				return DEFAULT_COST;
			}
		}

		public String getText() {
			if (increaseValue < 1)
				return "+" + increaseValue*100 + "% " + text;
			else
				return "+" + increaseValue + " " + text;
		}
	}

	private final ItemType type;

	public BuyItemCallback(ItemType type) {
		this.type = type;
	}

	@Override
	public void action(GameHandler gameHandler) {
		int cost = type.getCost(gameHandler.getPlayerInfo());
		if (gameHandler.getPlayerInfo().getCoins() >= cost) {
			gameHandler.getPlayerInfo().addCoins(-cost);
			gameHandler.getSoundManager().playPayingSound();
			switch (type) {
			case HP:
				gameHandler.getPlayerInfo().addHealthPoints(
						(int) (type.increaseValue));
				break;
			case DAMAGE:
				gameHandler.getPlayerInfo().addBonusDamage(
						(int) type.increaseValue);
				break;
			case SHOT_FREQUENCY:
				gameHandler.getPlayerInfo().addShotFrequencyFactor(
						type.increaseValue);
				break;
			case SHOT_SPEED:
				gameHandler.getPlayerInfo().addShotSpeedFactor(
						type.increaseValue);
				break;
			case SPEED:
				gameHandler.getPlayerInfo().addMaxSpeedFactor(
						type.increaseValue);
				break;
			}
		} else {
			Toast.makeText(gameHandler.getContext(), "Nicht genug Gold..",
					Toast.LENGTH_SHORT).show();
		}
	}

}
