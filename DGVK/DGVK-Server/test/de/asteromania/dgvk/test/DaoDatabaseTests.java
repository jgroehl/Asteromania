package de.asteromania.dgvk.test;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.asteromania.dgvk.dao.ScoreDao;
import de.asteromania.dgvk.dto.ScoreDto;

public class DaoDatabaseTests
{

	private static final String TESTDB = "testdb";
	private static Connection connection;
	private ScoreDao scoreDao = new ScoreDao();

	@BeforeClass
	public static void setupClass() throws SQLException
	{
		connectToDatabase("postgres");
		Statement s = connection.createStatement();
		s.executeUpdate("CREATE DATABASE " + TESTDB);
		connection.close();

	}

	@Before
	public void setup()
	{
		connectToDatabase(TESTDB);
		executeScript("create.sql", connection);
		scoreDao.setConnection(connection);
	}

	private static void connectToDatabase(String databaseName)
	{
		try
		{
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			Assert.fail("Could not find postgres jdbc driver.");
		}

		try
		{
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + databaseName, "postgres",
					"1234");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			Assert.fail("Could not connect to database");
		}

		Assert.assertNotNull("Connection not successfully established.", connection);
	}

	@Test
	public void testScoreDao() throws SQLException
	{
		final long testScore = 17;
		ScoreDto scoreDto = new ScoreDto(testScore);
		scoreDao.addScore(scoreDto);

		ScoreDto retrievedScore = scoreDao.getScores().getScores().get(0);

		Assert.assertEquals(scoreDto.getScore(), retrievedScore.getScore());
	}

	@After
	public void tearDown() throws SQLException
	{
		executeScript("drop.sql", connection);
		connection.close();
	}

	private void executeScript(String scriptFile, Connection connection)
	{
		try
		{
			String sqlScript = IOUtils.toString(this.getClass().getResourceAsStream(scriptFile),
					Charset.forName("UTF-8"));
			if (sqlScript != null && !sqlScript.isEmpty())
			{

				Statement s = connection.createStatement();
				s.execute(sqlScript);
				s.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@AfterClass
	public static void tearDownClass() throws SQLException
	{
		connectToDatabase("postgres");
		Statement s = connection.createStatement();
		s.executeUpdate("DROP DATABASE " + TESTDB);
	}

}
