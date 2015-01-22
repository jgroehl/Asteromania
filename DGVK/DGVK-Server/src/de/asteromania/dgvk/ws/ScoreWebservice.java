package de.asteromania.dgvk.ws;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;

import de.asteromania.dgvk.dao.ScoreDao;
import de.asteromania.dgvk.dto.ScoreDto;

@Path("score")
public class ScoreWebservice
{
	private final ScoreDao scoreDao = new ScoreDao();

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getScore()
	{
		System.out.println("GET score");
		try
		{
			return Response.ok().entity(scoreDao.getScores().toXml()).build();
		}
		catch (SQLException e)
		{
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response postScore(String xml)
	{
		System.out.println("POST score");
		try
		{
			scoreDao.addScore(ScoreDto.fromXml(xml));
			return Response.ok().build();
		}
		catch (SQLException e)
		{
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}
}
