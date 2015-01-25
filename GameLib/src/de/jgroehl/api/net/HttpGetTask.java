package de.jgroehl.api.net;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
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
		try
		{
			AndroidHttpClient client = getClient();
			HttpGet get = createGet(url);
			HttpResponse response = client.execute(get);
			HttpResult result = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response
					.getEntity()));
			client.close();
			return result;
		}
		catch (Exception e)
		{
			Log.e(TAG, "Error on GET Task: ", e);
			return null;
		}
	}

}