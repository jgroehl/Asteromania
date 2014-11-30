package de.jgroehl.asteromania.graphics.enemies;

import de.jgroehl.api.control.GameState;
import de.jgroehl.api.graphics.Target;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.graphics.bullets.Shot;
import android.content.Context;

public class BossEnemy extends BaseEnemy {

	private static final float BOSS_ENEMY_WIDTH = 0.12f;
	private static final float BOSS_SPEED = 0.005f;
	private static final int BOSS_SHOT_RATE = 780;
	private static final float BOSS_SHOT_SPEED = 0.019f;
	private static final int BOSS_LIFEPOINTS = 5;
	private static final int BOSS_DAMAGE = 3;

	public BossEnemy(float xPosition, float yPosition, int graphicsId,
			float relativeWidth, int frameCount, int animationPeriod,
			Context context, int lifepoints, float shotSpeed,
			int basicShootFrequency, float basicSpeed, float basicDamage) {
		super(xPosition, yPosition, graphicsId, relativeWidth, frameCount,
				animationPeriod, context, lifepoints, shotSpeed,
				basicShootFrequency, basicSpeed, basicDamage);
	}

	public static BossEnemy createBossEnemy(Context context, int level) {
		return new BossEnemy((float) Math.random(), 0.2f,
				de.jgroehl.asteromania.R.drawable.enemy2, BOSS_ENEMY_WIDTH, 6,
				100, context, BOSS_LIFEPOINTS * (level + 1), BOSS_SHOT_SPEED,
				BOSS_SHOT_RATE, BOSS_SPEED, BOSS_DAMAGE * level);
	}

	@Override
	protected void shoot(AsteromaniaGameHandler handler) {
		handler.getSoundManager().playEnemyShotSound();
		handler.add(new Shot(xPosition + relativeWidth / 2, yPosition
				+ relativeHeight * 0.4f, Target.PLAYER, getShotSpeed(),
				context, getShotDamage(), this), GameState.MAIN);
	}

}
