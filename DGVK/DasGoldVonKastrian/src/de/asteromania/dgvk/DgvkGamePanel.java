package de.asteromania.dgvk;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import de.jgroehl.api.AbstractGamePanel;
import de.jgroehl.api.activities.AbstractSimpleActivity;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.graphics.SimpleGraphicsObject;
import de.jgroehl.api.graphics.ui.Button;

public class DgvkGamePanel extends AbstractGamePanel
{

	private static final String TAG = DgvkGamePanel.class.getSimpleName();

	public DgvkGamePanel(Context context, AbstractSimpleActivity abstractMainActivity, BaseGameHandler gameHandler)
	{
		super(context, abstractMainActivity, gameHandler);
	}

	@Override
	public void initializeGameObjects()
	{
		GraphicsObject mainScreen = new SimpleGraphicsObject(0, 0, de.asteromania.dgvk.R.drawable.backside, 1,
				getContext());
		getBaseGameHandler().add(mainScreen, GameState.STARTING_SCREEN);
		getBaseGameHandler().update();
		getBaseGameHandler().add(new Button("Los", 0, 0.9f, 1f, 0.1f, new EventCallback()
		{

			@Override
			public void action(BaseGameHandler gameHandler)
			{
				Log.i(TAG, "Starting HttpPostRequest...");
				// new
				// DoHttpPostTask().execute("http://192.168.0.2:8080/DGVK-Server/rest/score",
				// new ScoreDto(new Random(
				// System.currentTimeMillis()).nextLong()).toXml());

				new DoHttpGetTask().execute("http://httpbin.org/ip");
			}
		}, getContext()), GameState.STARTING_SCREEN);
	}

	private class DoHttpGetTask extends AsyncTask<String, Long, String>
	{
		private final String TAG = DoHttpGetTask.class.getSimpleName();

		@Override
		protected String doInBackground(String... params)
		{
			try
			{
				AndroidHttpClient client = AndroidHttpClient.newInstance("androidClient", getContext());
				HttpGet get = new HttpGet(params[0]);
				HttpResponse response = client.execute(get);
				String string = EntityUtils.toString(response.getEntity());
				Log.i("RESULT", string);
				return string;
			}
			catch (Exception e)
			{
				Log.e(TAG, e.getLocalizedMessage());
				return null;
			}
		}

	}

	private class DoHttpPostTask extends AsyncTask<String, Long, Integer>
	{

		private final String TAG = DoHttpPostTask.class.getSimpleName();

		@Override
		protected Integer doInBackground(String... params)
		{
			try
			{
				AndroidHttpClient client = AndroidHttpClient.newInstance("androidClient", getContext());
				HttpPost post = new HttpPost(params[0]);
				post.setEntity(new StringEntity(params[1]));
				return client.execute(post).getStatusLine().getStatusCode();
			}
			catch (Exception e)
			{
				Log.e(TAG, e.getLocalizedMessage());
				return 0;
			}
		}

	}
}
