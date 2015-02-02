package de.jgroehl.asteromania.graphics.enemies;

import de.jgroehl.api.control.GameState;
import de.jgroehl.api.graphics.Target;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.control.callbacks.PurchaseItemCallback.PurchaseType;
import de.jgroehl.asteromania.graphics.bullets.Shot;
import de.jgroehl.asteromania.graphics.collectables.Ammo;
import de.jgroehl.asteromania.graphics.collectables.Coin;
import de.jgroehl.asteromania.graphics.collectables.Heart;
import android.content.Context;

public class BossEnemy extends BaseEnemy
{

	private static final float BOSS_ENEMY_WIDTH = 0.12f;
	private static final float BOSS_SPEED = 0.005f;
	private static final int BOSS_SHOT_RATE = 750;
	private static final float BOSS_SHOT_SPEED = 0.02f;
	private static final int BOSS_LIFEPOINTS = 7;
	private static final int BOSS_DAMAGE = 4;

	public BossEnemy(float xPosition, float yPosition, int graphicsId, float relativeWidth, int frameCount,
			int animationPeriod, Context context, int lifepoints, float shotSpeed, int basicShootFrequency,
			float basicSpeed, float basicDamage)
	{
		super(xPosition, yPosition, graphicsId, relativeWidth, frameCount, animationPeriod, context, lifepoints,
				shotSpeed, basicShootFrequency, basicSpeed, basicDamage);
	}

	public static BossEnemy createBossEnemy(Context context, int level)
	{
		return new BossEnemy((float) Math.random(), 0.2f, de.jgroehl.asteromania.R.drawable.enemy2, BOSS_ENEMY_WIDTH,
				6, 100, context, BOSS_LIFEPOINTS * (level + 1), BOSS_SHOT_SPEED, BOSS_SHOT_RATE, BOSS_SPEED,
				BOSS_DAMAGE * (level + 1) / 2);
	}

	@Override
	protected void shoot(AsteromaniaGameHandler handler)
	{
		handler.getSoundManager().playEnemyShotSound();
		handler.add(new Shot(getX() + relativeWidth / 2, getY() + relativeHeight * 0.4f, Target.PLAYER, getShotSpeed(),
				getContext(), getShotDamage(), this), GameState.MAIN);
	}

	@Override
	protected void dropItems(AsteromaniaGameHandler asteromaniaGameHandler)
	{
		int amountCoins = (int) ((0.25 + Math.random() * 0.125) * getShotDamage());
		Coin.addToHandler(amountCoins, asteromaniaGameHandler, this);
		if (asteromaniaGameHandler.getPlayerInfo().isMissingHealth() && Math.random() < 0.8f)
		{
			Heart.addToHandler(getShotDamage() / 2, asteromaniaGameHandler, this);
		}
		if (asteromaniaGameHandler.getPlayerInfo().purchasedItem(PurchaseType.ROCKET_LAUNCHER) && Math.random() < 0.9f)
			for (int i = 0, limit = (int) Math.random() * 5 + 1; i < limit; i++)
				Ammo.addToHandler(asteromaniaGameHandler, this);
	}

}
