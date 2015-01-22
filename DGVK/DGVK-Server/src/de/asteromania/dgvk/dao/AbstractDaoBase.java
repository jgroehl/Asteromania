package de.asteromania.dgvk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractDaoBase
{

	private static final String DATABASE_NAME = "dgvk";
	private Connection connection;

	public AbstractDaoBase()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}

	public Connection getConnection()
	{
		if (connection == null)
			openConnection();

		return connection;
	}

	public void closeConnection()
	{
		try
		{
			if (connection != null)
			{
				connection.close();
				connection = null;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void openConnection()
	{
		try
		{
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + DATABASE_NAME, "postgres",
					"1234");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
