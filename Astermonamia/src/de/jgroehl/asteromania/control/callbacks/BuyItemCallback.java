package de.jgroehl.asteromania.control.callbacks;

import android.widget.Toast;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.interfaces.EventCallback;
import de.jgroehl.asteromania.player.PlayerInfo;

public class BuyItemCallback implements EventCallback {

	public enum ItemType {

		HP(1, 5), DAMAGE(1, 3);

		public final float increaseValue;
		private final int baseCost;

		private ItemType(float increaseValue, int baseCost) {
			this.increaseValue = increaseValue;
			this.baseCost = baseCost;
		}

		public int getCost(PlayerInfo playerInfo) {
			switch (this) {
			case HP:
				return playerInfo.getHealthPoints().getMaximum() * baseCost;
			case DAMAGE:
				return (playerInfo.getBonusDamage() + baseCost)
						* (playerInfo.getBonusDamage() + baseCost);
			default:
				return 1;
			}
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
			switch (type) {
			case HP:
				gameHandler.getPlayerInfo().addHealthPoints(
						(int) (type.increaseValue));
				break;
			case DAMAGE:
				gameHandler.getPlayerInfo().addBonusDamage(
						(int) type.increaseValue);
				break;
			default:
				break;
			}
		} else {
			Toast.makeText(gameHandler.getContext(), "Nicht genug Gold..",
					Toast.LENGTH_SHORT).show();
		}
	}

}
