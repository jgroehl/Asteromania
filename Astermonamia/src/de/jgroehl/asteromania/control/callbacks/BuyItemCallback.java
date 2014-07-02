package de.jgroehl.asteromania.control.callbacks;

import android.widget.Toast;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.interfaces.EventCallback;

public class BuyItemCallback implements EventCallback {

	public enum ItemType {

		HP(100, 1);

		public final int cost;
		public final float increaseValue;

		private ItemType(int cost, float increaseValue) {
			this.cost = cost;
			this.increaseValue = increaseValue;
		}
	}

	private final ItemType type;

	public BuyItemCallback(ItemType type) {
		this.type = type;
	}

	@Override
	public void action(GameHandler gameHandler) {
		if (gameHandler.getPlayerInfo().getCoins() >= type.cost) {
			gameHandler.getPlayerInfo().addCoins(-type.cost);
			switch (type) {
			case HP:
				gameHandler.getPlayerInfo().addHealthPoints(
						(int) (type.increaseValue));
				break;
			}
		} else {
			Toast.makeText(gameHandler.getContext(), "Nicht genug Gold..",
					Toast.LENGTH_SHORT).show();
		}
	}

}
