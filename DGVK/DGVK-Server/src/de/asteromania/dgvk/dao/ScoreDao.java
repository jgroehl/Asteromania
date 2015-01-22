package de.asteromania.dgvk.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.asteromania.dgvk.dto.ScoreDto;
import de.asteromania.dgvk.dto.ScoreListDto;

public class ScoreDao extends AbstractDaoBase
{

	public ScoreListDto getScores() throws SQLException
	{
		List<ScoreDto> scores = new ArrayList<ScoreDto>();
		try
		{
			PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM score");
			if (ps.execute())
			{
				ResultSet rs = ps.getResultSet();
				while (rs.next())
				{
					scores.add(new ScoreDto(rs.getLong(1)));
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		return new ScoreListDto(scores);
	}

	public void addScore(ScoreDto score) throws SQLException
	{
		try
		{
			PreparedStatement ps = getConnection().prepareStatement("INSERT INTO score VALUES (?)");
			ps.setLong(1, score.getScore());
			ps.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
	}

}
