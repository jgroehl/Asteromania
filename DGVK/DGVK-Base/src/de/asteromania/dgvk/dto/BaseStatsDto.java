package de.asteromania.dgvk.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "baseStats")
public class BaseStatsDto
{
	@Element
	private int baseHealthPoints;

	@Element
	private int baseStrength;

	@Element
	private int baseMagicPower;

	@Element
	private int baseArmor;

	@Element
	private int baseMagicResist;

	@Element
	private int baseSpeed;

	@Element
	private int baseMagicPool;

	@Element
	private int baseHitPercentage;

	@Element
	private int baseCriticalHitPercentage;

	@Element
	private int baseEvasionPercentage;

	@Element
	private int baseInitiative;

	@Deprecated
	BaseStatsDto()
	{
		this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public BaseStatsDto(int baseHealthPoints, int baseStrength, int baseMagicPower, int baseArmor, int baseMagicResist,
			int baseSpeed, int baseMagicPool, int baseHitPercentage, int baseCriticalHitPercentage,
			int baseEvasionPercentage, int baseInitiative)
	{
		this.baseHealthPoints = baseHealthPoints;
		this.baseStrength = baseStrength;
		this.baseMagicPower = baseMagicPower;
		this.baseArmor = baseArmor;
		this.baseMagicResist = baseMagicResist;
		this.baseSpeed = baseSpeed;
		this.baseMagicPool = baseMagicPool;
		this.baseHitPercentage = baseHitPercentage;
		this.baseCriticalHitPercentage = baseCriticalHitPercentage;
		this.baseEvasionPercentage = baseEvasionPercentage;
		this.baseInitiative = baseInitiative;
	}

	public int getBaseHealthPoints()
	{
		return baseHealthPoints;
	}

	public void setBaseHealthPoints(int baseHealthPoints)
	{
		this.baseHealthPoints = baseHealthPoints;
	}

	public int getBaseStrength()
	{
		return baseStrength;
	}

	public void setBaseStrength(int baseStrength)
	{
		this.baseStrength = baseStrength;
	}

	public int getBaseMagicPower()
	{
		return baseMagicPower;
	}

	public void setBaseMagicPower(int baseMagicPower)
	{
		this.baseMagicPower = baseMagicPower;
	}

	public int getBaseArmor()
	{
		return baseArmor;
	}

	public void setBaseArmor(int baseArmor)
	{
		this.baseArmor = baseArmor;
	}

	public int getBaseMagicResist()
	{
		return baseMagicResist;
	}

	public void setBaseMagicResist(int baseMagicResist)
	{
		this.baseMagicResist = baseMagicResist;
	}

	public int getBaseSpeed()
	{
		return baseSpeed;
	}

	public void setBaseSpeed(int baseSpeed)
	{
		this.baseSpeed = baseSpeed;
	}

	public int getBaseMagicPool()
	{
		return baseMagicPool;
	}

	public void setBaseMagicPool(int baseMagicPool)
	{
		this.baseMagicPool = baseMagicPool;
	}

	public int getBaseHitPercentage()
	{
		return baseHitPercentage;
	}

	public void setBaseHitPercentage(int baseHitPercentage)
	{
		this.baseHitPercentage = baseHitPercentage;
	}

	public int getBaseCriticalHitPercentage()
	{
		return baseCriticalHitPercentage;
	}

	public void setBaseCriticalHitPercentage(int baseCriticalHitPercentage)
	{
		this.baseCriticalHitPercentage = baseCriticalHitPercentage;
	}

	public int getBaseEvasionPercentage()
	{
		return baseEvasionPercentage;
	}

	public void setBaseEvasionPercentage(int baseEvasionPercentage)
	{
		this.baseEvasionPercentage = baseEvasionPercentage;
	}

	public int getBaseInitiative()
	{
		return baseInitiative;
	}

	public void setBaseInitiative(int baseInitiative)
	{
		this.baseInitiative = baseInitiative;
	}
}
