package de.jgroehl.api.net;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;
import android.util.Log;

public class HttpGetTask extends AbstractHttpTask
{
	private static final String TAG = HttpGetTask.class.getSimpleName();
	private final String url;

	public HttpGetTask(String url, String username, OnResponseCallback callback)
	{
		super(username, callback);
		this.url = url;
	}

	@Override
	protected HttpResult doInBackground(String... params)
	{
		AndroidHttpClient client = getClient();
		try
		{
			HttpGet get = createGet(url);
			HttpResponse response = client.execute(get);
			HttpResult result = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response
					.getEntity()));
			client.close();
			return result;
		}
		catch (Exception e)
		{
			Log.e(TAG, "Error on HttpPost: " + e.getLocalizedMessage());
			if (e instanceof ConnectTimeoutException)
				return new HttpResult(HttpStatus.SC_GATEWAY_TIMEOUT, null);
			else
				return new HttpResult(0, null);
		}
		finally
		{
			client.close();
		}
	}

}