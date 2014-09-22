package de.jgroehl.api.graphics;

import android.content.Context;

public abstract class AbstractDamagingAbility extends GraphicsObject {

	private int damage;
	private Target target;

	public AbstractDamagingAbility(float xPosition, float yPosition,
			int graphicsId, float relativeWidth, Target target, int damage,
			Context context) {
		super(xPosition, yPosition, graphicsId, relativeWidth, context);
		this.target = target;
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public Target getTarget() {
		return target;
	}

}
