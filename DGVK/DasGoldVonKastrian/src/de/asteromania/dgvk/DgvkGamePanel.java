package de.asteromania.dgvk;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import de.asteromania.dgvk.dto.authentication.UserDto;
import de.jgroehl.api.AbstractGamePanel;
import de.jgroehl.api.activities.AbstractSimpleActivity;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.graphics.SimpleGraphicsObject;
import de.jgroehl.api.graphics.ui.Button;
import de.jgroehl.api.net.AbstractHttpTask.OnResponseCallback;
import de.jgroehl.api.net.HttpGetTask;
import de.jgroehl.api.net.HttpPostTask;

public class DgvkGamePanel extends AbstractGamePanel
{

	protected static final String TAG = DgvkGamePanel.class.getSimpleName();

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
			public void action(final BaseGameHandler gameHandler)
			{
				try
				{
					new HttpPostTask("http://192.168.0.2:8080/DGVK-Server/rest/user/authenticate",
							new UserDto("A", "B").toXml(), new OnResponseCallback()
							{

								@Override
								public void onError(int resultCode)
								{
									Toast.makeText(getContext(), "Error on login: " + resultCode, Toast.LENGTH_LONG)
											.show();

								}

								@Override
								public void onSuccess(String result)
								{
									gameHandler.setState(GameState.MAIN);
								}

							}).execute();
				}
				catch (Exception e)
				{
					Log.e(TAG, "Error: ", e);
				}
			}
		}, getContext()), GameState.STARTING_SCREEN);
	}
}
