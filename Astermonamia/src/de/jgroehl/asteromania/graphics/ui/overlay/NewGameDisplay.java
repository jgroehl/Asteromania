package de.jgroehl.asteromania.graphics.ui.overlay;

import android.content.Context;
import android.graphics.Canvas;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.graphics.ui.Alignment;
import de.jgroehl.api.graphics.ui.Label;
import de.jgroehl.asteromania.control.PlayerInfo;

public class NewGameDisplay extends GraphicsObject
{

	private final PlayerInfo playerInfo;
	private final Label currentLevel;
	private final Label checkpointLevel;

	public NewGameDisplay(Context context, PlayerInfo playerInfo)
	{
		super(0, 0, 1, 1, context);

		if (playerInfo == null)
			throw new NullPointerException("PlayerInfo");

		this.playerInfo = playerInfo;

		currentLevel = new Label("Level: " + playerInfo.getLevel(), 0.5f, 0.28f, context);
		currentLevel.setAlignment(Alignment.CENTER_HORIZONTALLY);
		checkpointLevel = new Label("Checkpoint Level: " + playerInfo.getCheckpointLevel(), 0.5f, 0.68f, context);
		checkpointLevel.setAlignment(Alignment.CENTER_HORIZONTALLY);

	}

	@Override
	public void draw(Canvas c)
	{
		currentLevel.setText("Level: " + playerInfo.getLevel());
		checkpointLevel.setText("Checkpoint Level: " + playerInfo.getCheckpointLevel());

		currentLevel.draw(c);
		checkpointLevel.draw(c);
	}

	@Override
	public void update(BaseGameHandler gameHandler)
	{
	}

}
