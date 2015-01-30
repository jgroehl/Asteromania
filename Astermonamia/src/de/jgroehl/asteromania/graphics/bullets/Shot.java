package de.jgroehl.asteromania.graphics.bullets;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.AbstractDamagingAbility;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.graphics.Target;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.graphics.enemies.BaseEnemy;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;

public class Shot extends AbstractDamagingAbility
{

	private static final float RELATIVE_WIDTH = 0.01f;

	private final float direction;
	private final float shotSpeed;
	private final BaseEnemy source;

	public Shot(float xPosition, float yPosition, Target target, float shotSpeed, Context context, int damage,
			BaseEnemy source)
	{
		super(xPosition, yPosition, (target.equals(Target.PLAYER) ? de.jgroehl.asteromania.R.drawable.normal_shot_down
				: de.jgroehl.asteromania.R.drawable.normal_shot_up), RELATIVE_WIDTH, target, damage, context);

		direction = target.equals(Target.PLAYER) ? 1.0f : -1.0f;
		this.shotSpeed = shotSpeed;
		this.source = source;
	}

	@Override
	public void update(BaseGameHandler handler)
	{

		if (source != null && !source.isAlive())
			handler.remove(this);

		for (Hitable hitable : ((AsteromaniaGameHandler) handler).getHitableObjects())
		{
			GraphicsObject go = (GraphicsObject) hitable;
			if (imagesOverlap(getX(), getY(), getRelativeWidth(), getRelativeHeight(), go.getX(), go.getY(),
					go.getRelativeWidth(), go.getRelativeHeight()))
			{
				if (hitable.isAlive())
					hitable.getShot(handler, this);
			}
		}

		yPosition = yPosition + (shotSpeed * direction);
		if (yPosition > 1.1f || yPosition < -0.1f)
		{
			handler.remove(this);
			if (getTarget().equals(Target.ENEMY))
				((AsteromaniaGameHandler) handler).getPlayerInfo().incrementMisses();
		}

	}
}
