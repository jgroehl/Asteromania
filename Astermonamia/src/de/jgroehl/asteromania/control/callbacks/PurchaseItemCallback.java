package de.jgroehl.asteromania.control.callbacks;

import android.content.Context;
import android.widget.Toast;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.api.graphics.ui.SimpleClickableElement;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;

public class PurchaseItemCallback implements EventCallback
{

	public enum PurchaseType
	{
		SHIELD_GENERATOR(750, R.string.shield_generator), DOUBLE_SHOT(2000, R.string.double_shot), TRIPLE_SHOT(6000,
				R.string.triple_shot), ROCKET_LAUNCHER(1000, R.string.rocket_launcher);

		public final int cost;
		public final int textId;

		private PurchaseType(int cost, int textId)
		{
			this.cost = cost;
			this.textId = textId;
		}
	}

	private final PurchaseType type;
	private final Context context;

	public PurchaseItemCallback(PurchaseType type, Context context)
	{
		this.type = type;
		this.context = context;
	}

	@Override
	public void action(BaseGameHandler gameHandler)
	{

		if (gameHandler instanceof AsteromaniaGameHandler)
		{

			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;

			if (!asteromaniaGameHandler.getPlayerInfo().purchasedItem(type))
			{
				if (asteromaniaGameHandler.getPlayerInfo().getCoins() >= type.cost)
				{
					if (type == PurchaseType.TRIPLE_SHOT
							&& !asteromaniaGameHandler.getPlayerInfo().purchasedDoubleShot())
					{
						Toast.makeText(context, context.getResources().getString(R.string.purchase_doubleshot_first),
								Toast.LENGTH_LONG).show();
					}
					else
					{
						asteromaniaGameHandler.getPlayerInfo().addCoins(-type.cost);
						asteromaniaGameHandler.getSoundManager().playPayingSound();
						asteromaniaGameHandler.getPlayerInfo().addPurchase(type);
						if (type.equals(PurchaseType.ROCKET_LAUNCHER))
							asteromaniaGameHandler.add(
									new SimpleClickableElement(0.02f, 0.75f, R.drawable.shotfield_rocket, 0.3f,
											new RandomTargetCallback(), gameHandler.getContext()), GameState.MAIN);
						asteromaniaGameHandler.getApiHandler().unlockPurchaseAchievement(type);
					}
				}
				else
				{
					Toast.makeText(context, context.getResources().getString(R.string.not_enough_gold),
							Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				Toast.makeText(context, context.getResources().getString(R.string.already_purchased_item),
						Toast.LENGTH_LONG).show();
			}
		}

	}

}
