package de.jgroehl.asteromania.graphics.enemies;

import android.content.Context;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.graphics.Target;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.graphics.bullets.Shot;
import de.jgroehl.asteromania.graphics.collectables.Coin;
import de.jgroehl.asteromania.graphics.collectables.Heart;

public class NormalEnemy extends BaseEnemy
{

	private static final float NORMAL_ENEMY_WIDTH = 0.14f;
	private static final float NORMAL_SPEED = 0.01f;
	private static final int NORMAL_SHOT_RATE = 1500;
	private static final float NORMAL_SHOT_SPEED = 0.015f;
	private static final int NORMAL_LIFEPOINTS = 3;
	private static final float NORMAL_DAMAGE = 1;

	private NormalEnemy(float xPosition, float yPosition, int graphicsId, float relativeWidth, int frameCount,
			int animationPeriod, Context context, int lifepoints, float shotSpeed, int basicShootFrequency,
			float basicSpeed, float basicDamage)
	{
		super(xPosition, yPosition, graphicsId, relativeWidth, frameCount, animationPeriod, context, lifepoints,
				shotSpeed, basicShootFrequency, basicSpeed, basicDamage);
	}

	public static NormalEnemy createNormalEnemy(Context context, int level)
	{
		return new NormalEnemy((float) Math.random(), 0.2f, de.jgroehl.asteromania.R.drawable.enemy,
				NORMAL_ENEMY_WIDTH, 15, 100, context, NORMAL_LIFEPOINTS * (level + 1), NORMAL_SHOT_SPEED,
				NORMAL_SHOT_RATE, NORMAL_SPEED, Math.round(NORMAL_DAMAGE * (level + 2) / 3));
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
		int amountCoins = (int) ((0.5 + Math.random() * 0.25) * getShotDamage() + 1);
		Coin.addToHandler(amountCoins, asteromaniaGameHandler, this);
		if (asteromaniaGameHandler.getPlayerInfo().isMissingHealth() && Math.random() < 0.4f)
		{
			Heart.addToHandler(getShotDamage() / 2, asteromaniaGameHandler, this);
		}
	}

}
