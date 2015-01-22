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
	private final OnResponseCallback callback;

	public HttpGetTask(String url)
	{
		this(url, null);
	}

	public HttpGetTask(String url, OnResponseCallback callback)
	{
		this.url = url;
		this.callback = callback;
	}

	@Override
	protected String doInBackground(String... params)
	{
		try
		{
			AndroidHttpClient client = AndroidHttpClient.newInstance(CLIENT_NAME);
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			String result = EntityUtils.toString(response.getEntity());
			if (callback != null)
				callback.performTask(result);
			return result;
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getLocalizedMessage());
			return null;
		}
	}

}