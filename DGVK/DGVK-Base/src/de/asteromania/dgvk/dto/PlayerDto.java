package de.asteromania.dgvk.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import de.asteromania.dgvk.dto.authentication.UserDto;

@Root(name = "player")
public class PlayerDto extends BaseDto<PlayerDto>
{
	@Element
	private UserDto associatedUser;

	@Element
	private StatsDto stats;

	public PlayerDto(StatsDto stats)
	{
		this.stats = stats;
	}

	public StatsDto getStats()
	{
		return stats;
	}

	public UserDto getAssociatedUser()
	{
		return associatedUser;
	}
}
