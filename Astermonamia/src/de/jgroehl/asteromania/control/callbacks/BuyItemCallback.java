package de.jgroehl.asteromania.control.callbacks;

import android.content.res.Resources;
import android.widget.Toast;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.control.PlayerInfo;
import de.jgroehl.asteromania.control.callbacks.PurchaseItemCallback.PurchaseType;

public class BuyItemCallback implements EventCallback
{

	public static final int DEFAULT_COST = 1000;

	public enum ItemType
	{

		HP(1, 2, R.string.lifepoint, false), DAMAGE(1, 3,

		R.string.damage, false), SPEED(0.1f, 1, R.string.speed, false), SHOT_SPEED(0.1f, 2, R.string.shot_speed, false), SHOT_FREQUENCY(
				0.1f, 2, R.string.shot_frequency, false), SHIELD_SECONDS(5, 2, R.string.shield, true), AMMO(1, 20,
				R.string.rocket_ammo, true);

		public final float increaseValue;
		private final int baseCost;
		private final int textId;
		private final boolean consumable;

		private ItemType(float increaseValue, int baseCost, int text, boolean consumable)
		{
			this.increaseValue = increaseValue;
			this.baseCost = baseCost;
			this.textId = text;
			this.consumable = consumable;
		}

		public int getCost(PlayerInfo playerInfo)
		{
			switch (this)
			{
				case HP:
					return (int) (playerInfo.getHealthPoints().getMaximum() * baseCost);
				case DAMAGE:
					return (playerInfo.getBonusDamage() + baseCost) * (playerInfo.getBonusDamage() + baseCost);
				case SPEED:
					return (int) Math.pow((((playerInfo.getMaxSpeedFactor() - 1) * 6) + baseCost), 3);
				case SHOT_FREQUENCY:
					return (int) Math.pow((((playerInfo.getShotFrequencyFactor() - 1) * 12) + baseCost), 3);
				case SHOT_SPEED:
					return (int) Math.pow((((playerInfo.getShotSpeedFactor() - 1) * 9) + baseCost), 3);
				case SHIELD_SECONDS:
					return (int) (baseCost * playerInfo.getLevel());
				case AMMO:
					return baseCost;
				default:
					return DEFAULT_COST;
			}
		}

		public String getText(Resources resources)
		{
			if (this == ItemType.SHIELD_SECONDS)
			{
				return "+" + (int) increaseValue + " " + resources.getString(R.string.seconds) + " "
						+ resources.getString(textId);
			}
			if (increaseValue < 1)
				return "+" + (int) (increaseValue * 100) + "% " + resources.getString(textId);
			else
				return "+" + (int) increaseValue + " " + resources.getString(textId);
		}
	}

	private final ItemType type;

	public BuyItemCallback(ItemType type)
	{
		this.type = type;
	}

	@Override
	public void action(BaseGameHandler gameHandler)
	{

		if (gameHandler instanceof AsteromaniaGameHandler)
		{

			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;
			int cost = type.getCost(asteromaniaGameHandler.getPlayerInfo());
			if (type == ItemType.SHIELD_SECONDS)
			{
				if (!asteromaniaGameHandler.getPlayerInfo().isShieldGeneratorPresent())
				{
					Toast.makeText(
							asteromaniaGameHandler.getContext(),
							asteromaniaGameHandler.getContext().getResources()
									.getString(de.jgroehl.asteromania.R.string.purchase_shield_generator_first),
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
			else if (type == ItemType.AMMO)
			{
				if (!asteromaniaGameHandler.getPlayerInfo().purchasedItem(PurchaseType.ROCKET_LAUNCHER))
				{
					Toast.makeText(
							asteromaniaGameHandler.getContext(),
							asteromaniaGameHandler.getContext().getResources()
									.getString(de.jgroehl.asteromania.R.string.purchase_rocket_launcher_first),
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
			if (asteromaniaGameHandler.getPlayerInfo().getCoins() >= cost)
			{
				if (type.consumable)
					asteromaniaGameHandler.getPlayerInfo().payConsumable(cost);
				else
					asteromaniaGameHandler.getPlayerInfo().addCoins(-cost);
				asteromaniaGameHandler.getSoundManager().playPayingSound();
				switch (type)
				{
					case HP:
						asteromaniaGameHandler.getPlayerInfo().addHealthPoints((int) (type.increaseValue));
						break;
					case DAMAGE:
						asteromaniaGameHandler.getPlayerInfo().addBonusDamage((int) type.increaseValue);
						break;
					case SHOT_FREQUENCY:
						asteromaniaGameHandler.getPlayerInfo().addShotFrequencyFactor(type.increaseValue);
						break;
					case SHOT_SPEED:
						asteromaniaGameHandler.getPlayerInfo().addShotSpeedFactor(type.increaseValue);
						break;
					case SPEED:
						asteromaniaGameHandler.getPlayerInfo().addMaxSpeedFactor(type.increaseValue);
						break;
					case SHIELD_SECONDS:
						asteromaniaGameHandler.getPlayer().addShieldSeconds((int) type.increaseValue);
					case AMMO:
						asteromaniaGameHandler.getPlayerInfo().incrementAmmo();
						break;
				}
			}
			else
			{
				Toast.makeText(
						asteromaniaGameHandler.getContext(),
						asteromaniaGameHandler.getContext().getResources()
								.getString(de.jgroehl.asteromania.R.string.not_enough_gold), Toast.LENGTH_SHORT).show();
			}
		}
	}

}
