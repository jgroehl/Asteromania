package de.asteromania.dgvk.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "player")
public class PlayerDto
{
	@Element(name = "stats")
	private BaseStatsDto baseStats;

	@Element
	private int currentHealthPoints;

	public PlayerDto(BaseStatsDto stats, int currentHealthPoints)
	{
		this.baseStats = stats;
		this.currentHealthPoints = currentHealthPoints;
	}

	public BaseStatsDto getBaseStats()
	{
		return baseStats;
	}
}
