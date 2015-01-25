package de.jgroehl.api.net;

public class HttpResult
{
	private final int statusCode;
	private final String xmlResponse;

	public HttpResult(int statusCode, String xmlResponse)
	{
		this.statusCode = statusCode;
		this.xmlResponse = xmlResponse;
	}

	public int getStatusCode()
	{
		return statusCode;
	}

	public String getXmlResponse()
	{
		return xmlResponse;
	}
}
