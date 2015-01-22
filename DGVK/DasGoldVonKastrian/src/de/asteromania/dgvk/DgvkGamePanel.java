package de.asteromania.dgvk;

import java.util.Random;

import android.content.Context;
import android.util.Log;
import de.asteromania.dgvk.dto.ScoreDto;
import de.jgroehl.api.AbstractGamePanel;
import de.jgroehl.api.activities.AbstractSimpleActivity;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.graphics.SimpleGraphicsObject;
import de.jgroehl.api.graphics.ui.Button;
import de.jgroehl.api.net.HttpPostTask;

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
				new HttpPostTask("http://192.168.0.2:8080/DGVK-Server/rest/score", new ScoreDto(new Random(
						System.currentTimeMillis()).nextLong()).toXml()).execute();
			}
		}, getContext()), GameState.STARTING_SCREEN);
	}
}
