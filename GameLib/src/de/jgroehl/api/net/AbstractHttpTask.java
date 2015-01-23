package de.jgroehl.api.net;

import android.os.AsyncTask;
import de.jgroehl.api.net.AbstractHttpTask.HttpResult;

public abstract class AbstractHttpTask extends AsyncTask<String, Void, HttpResult>
{

	protected static final String CLIENT_NAME = "jgroehl-Android-Client";

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

	public AbstractHttpTask()
	{
	}

	public interface OnResponseCallback
	{
		void onSuccess(String result);

		void onError(int resultCode);
	}

}
