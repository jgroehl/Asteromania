package de.asteromania.dgvk.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;

import de.asteromania.dgvk.dao.UserDao;
import de.asteromania.dgvk.dto.authentication.UserDto;

@Path("user")
public class AuthenticationWebservice
{
	private final UserDao userDao = new UserDao();
	private final List<UserDto> authenticatedUsers = new ArrayList<>();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getUsers()
	{
		return "Hallo Janek";
	}

	@POST
	@Path("authenticate")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response getScore(String userDtoXml)
	{
		System.out.println("POST authenticate");
		UserDto user = new UserDto("", "");
		try
		{
			user = user.fromXml(userDtoXml);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return Response.serverError().build();
		}

		System.out.println("Trying to authenticate user " + user.getUsername());

		if (authenticatedUsers.contains(user))
		{
			return Response.ok().build();
		}
		else
		{
			System.out.println("Sending " + Status.FORBIDDEN);
			return Response.status(Status.FORBIDDEN).build();
		}
	}

}
