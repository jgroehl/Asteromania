package de.asteromania.dgvk.net;

public final class DgvkUrlProperties
{

	private static final String SERVER_BASE = "http://192.168.0.2:8080/DGVK-Server/";
	private static final String REST_BASE = "rest/";

	private DgvkUrlProperties()
	{
	}

	public static String authenticationUrl()
	{
		return SERVER_BASE + REST_BASE + "user/authenticate";
	}

	public static String logoutUrl()
	{
		return SERVER_BASE + REST_BASE + "user/logout";
	}

}
