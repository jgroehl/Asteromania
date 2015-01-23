package de.jgroehl.api.net;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

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
	protected HttpResult doInBackground(String... params)
	{
		try
		{
			AndroidHttpClient client = AndroidHttpClient.newInstance(CLIENT_NAME);
			HttpPost post = new HttpPost(url);
			post.setHeader("content-type", "application/xml");
			if (xmlData != null)
				post.setEntity(new StringEntity(xmlData));
			Log.d(TAG, "POST on " + url);
			HttpResponse response = client.execute(post);
			HttpResult result = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response
					.getEntity()));
			client.close();
			return result;
		}
		catch (Exception e)
		{
			Log.e(TAG, "Error on HttpPost:", e);
			return new HttpResult(0, null);
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
