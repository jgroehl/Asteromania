package de.asteromania.dgvk.dto.authentication;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import de.asteromania.dgvk.dto.BaseDto;

@Root(name = "user")
public class UserDto extends BaseDto<UserDto>
{

	@Element
	private String username;

	@Element
	private String password;

	@Element
	private String role;

	@Deprecated
	UserDto()
	{
		this("", "");
	}

	public UserDto(String username, String password)
	{
		this(username, password, "None");
	}

	public UserDto(String username, String password, String role)
	{
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

}
