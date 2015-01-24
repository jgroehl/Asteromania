package de.asteromania.dgvk;

import android.content.Context;
import de.asteromania.dgvk.control.DgvkGameHandler;
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
				((DgvkGameHandler) gameHandler).getContext().openLoginScreen();
			}
		}, getContext()), GameState.STARTING_SCREEN);
	}
}
