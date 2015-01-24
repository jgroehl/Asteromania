package de.asteromania.dgvk.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.asteromania.dgvk.dto.authentication.UserDto;

public class UserDao extends AbstractDaoBase
{

	public UserDto getUser(String username, String password) throws SQLException
	{
		PreparedStatement s = getConnection().prepareStatement(
				"SELECT username, password, role FROM users WHERE username = ? AND password = ?");
		s.setString(1, username);
		s.setString(2, password);
		s.execute();

		ResultSet rs = s.getResultSet();

		if (rs.next())
		{
			String name = rs.getString(1);
			String pw = rs.getString(2);
			String role = rs.getString(3);
			return new UserDto(name, pw, role);
		}
		else
		{
			return null;
		}
	}

	public void addUser(UserDto user) throws SQLException
	{
		PreparedStatement s = getConnection().prepareStatement("INSERT INTO users VALUES(?,?,?)");
		s.setString(1, user.getUsername());
		s.setString(2, user.getPassword());
		s.setString(3, user.getRole());
		s.execute();
	}

}
