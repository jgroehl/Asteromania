package de.asteromania.dgvk.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.asteromania.dgvk.dto.ScoreDto;

@Path("score")
public class ScoreWebservice
{
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getScore()
	{
		return Response.ok().entity(new ScoreDto((long) (Math.random() * Long.MAX_VALUE)).toXml()).build();
	}
}
