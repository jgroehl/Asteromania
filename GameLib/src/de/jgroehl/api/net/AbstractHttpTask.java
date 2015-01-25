package de.jgroehl.api.net;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.HttpConnectionParams;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

public abstract class AbstractHttpTask extends AsyncTask<String, Void, HttpResult>
{

	private static final String CLIENT_NAME = "DGVK-Android-Client";
	private static final String TAG = AbstractHttpTask.class.getSimpleName();
	private final String username;
	private final OnResponseCallback callback;

	public AbstractHttpTask(String username, OnResponseCallback callback)
	{
		this.username = username;
		this.callback = callback;
	}

	protected AndroidHttpClient getClient()
	{
		AndroidHttpClient client = AndroidHttpClient.newInstance(CLIENT_NAME);
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 3000);
		HttpConnectionParams.setSoTimeout(client.getParams(), 5000);
		return client;
	}

	protected HttpGet createGet(String url)
	{
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("username", username);
		return httpGet;
	}

	protected HttpPost createPost(String url, String xmlData) throws UnsupportedEncodingException
	{
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("content-type", "application/xml");
		httpPost.setHeader("username", username);
		if (xmlData != null)
			httpPost.setEntity(new StringEntity(xmlData));
		return httpPost;
	}

	public interface OnResponseCallback
	{
		void onSuccess(String result);

		void onError(int resultCode);
	}

	@Override
	protected void onPostExecute(HttpResult result)
	{
		if (callback != null)
		{
			if (HttpStatus.SC_OK == result.getStatusCode())
			{
				Log.d(TAG, "Sucess: " + result.getStatusCode());
				callback.onSuccess(result.getXmlResponse());
			}
			else
				Log.d(TAG, "Error: " + result.getStatusCode());
			callback.onError(result.getStatusCode());
		}
	}

}
