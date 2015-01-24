package de.asteromania.dgvk.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;

import de.asteromania.dgvk.dao.UserDao;
import de.asteromania.dgvk.dto.authentication.UserDto;
import de.asteromania.dgvk.utils.HashUtils;

@Path("user")
public class AuthenticationWebservice
{
	private static final int HASH_COUNT = 1013;
	private static final String CLIENT_ROLE = "standard-android-client";
	private static final String SALT_SECRET = "fb74d31d-cbf7-43e9-8053-66a14395ad17";
	private final UserDao userDao = new UserDao();
	private static final List<UserDto> authenticatedUsers = new ArrayList<>();

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addUser(String userDtoXml)
	{
		System.out.println("POST user");
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

		try
		{
			UserDto authUser = new UserDto(user.getUsername(), HashUtils.hashString(
					user.getPassword() + user.getUsername() + SALT_SECRET, HASH_COUNT), CLIENT_ROLE);
			authenticatedUsers.add(authUser);
			return Response.status(Status.OK).build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("authenticate")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response getScore(String userDtoXml)
	{
		System.out.println("POST user/authenticate");
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

		if (authenticatedUsers.size() > 0)
			System.out.println("Chache user: " + authenticatedUsers.get(0).getUsername());
		if (authenticatedUsers.contains(user))
		{
			System.out.println("Authenticated " + user.getUsername() + " by cache.");
			return Response.ok().build();
		}
		else
		{
			try
			{
				UserDto dbUser = userDao.getUser(user.getUsername(),
						HashUtils.hashString(user.getPassword() + user.getUsername() + SALT_SECRET, HASH_COUNT));
				if (dbUser != null)
				{
					System.out.println("Authenticated " + dbUser.getUsername() + " successfully.");
					authenticatedUsers.add(dbUser);
					return Response.status(Status.OK).entity(dbUser.toXml()).build();
				}
				else
				{
					System.out.println("Sending " + Status.FORBIDDEN);
					return Response.status(Status.FORBIDDEN).build();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
}
