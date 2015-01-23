package de.jgroehl.api.net;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
	protected HttpResult doInBackground(String... params)
	{
		try
		{
			AndroidHttpClient client = AndroidHttpClient.newInstance(CLIENT_NAME);
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			HttpResult result = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response
					.getEntity()));
			client.close();
			return result;
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getLocalizedMessage());
			return null;
		}
	}

	@Override
	protected void onPostExecute(HttpResult result)
	{
		if (callback != null)
		{
			if (HttpStatus.SC_OK == result.getStatusCode())
				callback.onSuccess(result.getXmlResponse());
			else
				callback.onError(result.getStatusCode());
		}
	}

}