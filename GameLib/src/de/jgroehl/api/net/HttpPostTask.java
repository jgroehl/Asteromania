package de.jgroehl.api.net;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import android.net.http.AndroidHttpClient;
import android.util.Log;

public class HttpPostTask extends AbstractHttpTask
{

	private final String TAG = HttpPostTask.class.getSimpleName();
	private final String url;
	private final String xmlData;
	private final OnResponseCallback callback;

	public HttpPostTask(String url, String xmlData)
	{
		this(url, xmlData, null);
	}

	public HttpPostTask(String url, String xmlData, OnResponseCallback callback)
	{
		this.url = url;
		this.xmlData = xmlData;
		this.callback = callback;
	}

	@Override
	protected String doInBackground(String... params)
	{
		try
		{
			AndroidHttpClient client = AndroidHttpClient.newInstance(CLIENT_NAME);
			HttpPost post = new HttpPost(url);
			post.setHeader("content-type", "application/xml");
			post.setEntity(new StringEntity(xmlData));
			String value = String.valueOf(client.execute(post).getStatusLine().getStatusCode());
			if (callback != null)
				callback.performTask(value);
			return value;
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getLocalizedMessage());
			return "0";
		}
	}

}
