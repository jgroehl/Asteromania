package de.jgroehl.asteromania.graphics.enemies;

import android.content.Context;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.graphics.Target;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.graphics.Shot;

public class NormalEnemy extends BaseEnemy {

	private static final float NORMAL_ENEMY_WIDTH = 0.14f;
	private static final float NORMAL_SPEED = 0.01f;
	private static final int NORMAL_SHOT_RATE = 1500;
	private static final float NORMAL_SHOT_SPEED = 0.025f;
	private static final int NORMAL_LIFEPOINTS = 3;
	private static final float NORMAL_DAMAGE = 1;

	private NormalEnemy(float xPosition, float yPosition, int graphicsId,
			float relativeWidth, int frameCount, int animationPeriod,
			Context context, int lifepoints, float shotSpeed,
			int basicShootFrequency, float basicSpeed, float basicDamage) {
		super(xPosition, yPosition, graphicsId, relativeWidth, frameCount,
				animationPeriod, context, lifepoints, shotSpeed,
				basicShootFrequency, basicSpeed, basicDamage);
	}

	public static NormalEnemy createNormalEnemy(Context context, int level) {
		return new NormalEnemy((float) Math.random(), 0.2f,
				de.jgroehl.asteromania.R.drawable.enemy, NORMAL_ENEMY_WIDTH,
				15, 100, context, NORMAL_LIFEPOINTS * (level + 1),
				NORMAL_SHOT_SPEED, NORMAL_SHOT_RATE, NORMAL_SPEED,
				NORMAL_DAMAGE * level);
	}

	@Override
	protected void shoot(AsteromaniaGameHandler handler) {
		handler.getSoundManager().playEnemyShotSound();
		handler.add(new Shot(xPosition + relativeWidth / 2, yPosition
				+ relativeHeight * 0.4f, Target.PLAYER, getShotSpeed(), context,
				getShotDamage(), this), GameState.MAIN);
	}

}
