package de.jgroehl.asteromania.control.callbacks;

import android.content.res.Resources;
import android.widget.Toast;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.PlayerInfo;
import de.jgroehl.asteromania.control.interfaces.EventCallback;

public class BuyItemCallback implements EventCallback {

	public static final int DEFAULT_COST = 1000;

	public enum ItemType {

		HP(1, 2, R.string.lifepoint), DAMAGE(1, 3,

		R.string.damage), SPEED(0.1f, 1, R.string.speed), SHOT_SPEED(0.1f, 2,
				R.string.shot_speed), SHOT_FREQUENCY(0.1f, 2,
				R.string.shot_frequency), SHIELD_SECONDS(5, 2, R.string.shield);

		public final float increaseValue;
		private final int baseCost;
		private final int textId;

		private ItemType(float increaseValue, int baseCost, int text) {
			this.increaseValue = increaseValue;
			this.baseCost = baseCost;
			this.textId = text;
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
			case SHIELD_SECONDS:
				return (int) (baseCost * playerInfo.getLevel());
			default:
				return DEFAULT_COST;
			}
		}

		public String getText(Resources resources) {
			if (this == ItemType.SHIELD_SECONDS) {
				return "+" + (int) increaseValue + " "
						+ resources.getString(R.string.seconds) + " "
						+ resources.getString(textId);
			}
			if (increaseValue < 1)
				return "+" + (int) (increaseValue * 100) + "% "
						+ resources.getString(textId);
			else
				return "+" + (int) increaseValue + " "
						+ resources.getString(textId);
		}
	}

	private final ItemType type;

	public BuyItemCallback(ItemType type) {
		this.type = type;
	}

	@Override
	public void action(GameHandler gameHandler) {
		int cost = type.getCost(gameHandler.getPlayerInfo());
		if (type == ItemType.SHIELD_SECONDS) {
			if (!gameHandler.getPlayerInfo().isShieldGeneratorPresent()) {
				Toast.makeText(
						gameHandler.getContext(),
						gameHandler
								.getContext()
								.getResources()
								.getString(
										de.jgroehl.asteromania.R.string.purchase_shield_generator_first),
						Toast.LENGTH_SHORT).show();
				return;
			}
		}
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
			case SHIELD_SECONDS:
				gameHandler.getPlayer().addShieldSeconds(
						(int) type.increaseValue);
				break;
			}
		} else {
			Toast.makeText(
					gameHandler.getContext(),
					gameHandler
							.getContext()
							.getResources()
							.getString(
									de.jgroehl.asteromania.R.string.not_enough_gold),
					Toast.LENGTH_SHORT).show();
		}
	}

}
