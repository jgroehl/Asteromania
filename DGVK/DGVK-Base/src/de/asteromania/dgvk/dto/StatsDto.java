package de.asteromania.dgvk.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "stats")
public class StatsDto extends BaseDto<StatsDto>
{
	@Element
	private int healthPoints;

	@Element
	private int strength;

	@Element
	private int magicPower;

	@Element
	private int speed;

	@Element
	private int magicPool;

	@Element
	private int hitPercentage;

	@Element
	private int criticalHitPercentage;

	@Element
	private int evasionPercentage;

	@Element
	private int initiative;

	@Deprecated
	StatsDto()
	{
		this(0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public StatsDto(int baseHealthPoints, int baseStrength, int baseMagicPower, int baseSpeed, int baseMagicPool,
			int baseHitPercentage, int baseCriticalHitPercentage, int baseEvasionPercentage, int baseInitiative)
	{
		this.healthPoints = baseHealthPoints;
		this.strength = baseStrength;
		this.magicPower = baseMagicPower;
		this.speed = baseSpeed;
		this.magicPool = baseMagicPool;
		this.hitPercentage = baseHitPercentage;
		this.criticalHitPercentage = baseCriticalHitPercentage;
		this.evasionPercentage = baseEvasionPercentage;
		this.initiative = baseInitiative;
	}

	public int getBaseHealthPoints()
	{
		return healthPoints;
	}

	public void setBaseHealthPoints(int baseHealthPoints)
	{
		this.healthPoints = baseHealthPoints;
	}

	public int getBaseStrength()
	{
		return strength;
	}

	public void setBaseStrength(int baseStrength)
	{
		this.strength = baseStrength;
	}

	public int getBaseMagicPower()
	{
		return magicPower;
	}

	public void setBaseMagicPower(int baseMagicPower)
	{
		this.magicPower = baseMagicPower;
	}

	public int getBaseSpeed()
	{
		return speed;
	}

	public void setBaseSpeed(int baseSpeed)
	{
		this.speed = baseSpeed;
	}

	public int getBaseMagicPool()
	{
		return magicPool;
	}

	public void setBaseMagicPool(int baseMagicPool)
	{
		this.magicPool = baseMagicPool;
	}

	public int getBaseHitPercentage()
	{
		return hitPercentage;
	}

	public void setBaseHitPercentage(int baseHitPercentage)
	{
		this.hitPercentage = baseHitPercentage;
	}

	public int getBaseCriticalHitPercentage()
	{
		return criticalHitPercentage;
	}

	public void setBaseCriticalHitPercentage(int baseCriticalHitPercentage)
	{
		this.criticalHitPercentage = baseCriticalHitPercentage;
	}

	public int getBaseEvasionPercentage()
	{
		return evasionPercentage;
	}

	public void setBaseEvasionPercentage(int baseEvasionPercentage)
	{
		this.evasionPercentage = baseEvasionPercentage;
	}

	public int getBaseInitiative()
	{
		return initiative;
	}

	public void setBaseInitiative(int baseInitiative)
	{
		this.initiative = baseInitiative;
	}
}
