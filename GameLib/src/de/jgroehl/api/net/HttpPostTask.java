package de.jgroehl.api.net;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;
import android.util.Log;

public class HttpPostTask extends AbstractHttpTask
{

	private final String TAG = HttpPostTask.class.getSimpleName();
	private final String url;
	private final String xmlData;

	public HttpPostTask(String url, String xmlData, String username, OnResponseCallback callback)
	{
		super(username, callback);
		this.url = url;
		this.xmlData = xmlData;
	}

	@Override
	protected HttpResult doInBackground(String... params)
	{
		AndroidHttpClient client = getClient();
		try
		{
			HttpPost post = createPost(url, xmlData);
			Log.d(TAG, "POST on " + url);
			HttpResponse response = client.execute(post);

			return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
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
