package de.jgroehl.asteromania.control.callbacks;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.games.Games;

import de.jgroehl.api.activities.AbstractGooglePlayGamesLoginActivity;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;

public class ShowLeaderboardCallback implements EventCallback
{

	private static final int LEADERBOARD_REQUEST = 101;

	@Override
	public void action(BaseGameHandler gameHandler)
	{
		if (gameHandler instanceof AsteromaniaGameHandler)
		{

			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;

			if (asteromaniaGameHandler.getContext() instanceof AbstractGooglePlayGamesLoginActivity)
			{
				if (asteromaniaGameHandler.getApiHandler().getApiClient().isConnected())
				{
					((AbstractGooglePlayGamesLoginActivity) asteromaniaGameHandler.getContext())
							.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(asteromaniaGameHandler
									.getApiHandler().getApiClient(), asteromaniaGameHandler.getContext().getResources()
									.getString(R.string.leaderboard_highscore)), LEADERBOARD_REQUEST);
				}
				else
				{
					Toast.makeText(asteromaniaGameHandler.getContext(), R.string.no_connection_to_play_services,
							Toast.LENGTH_LONG).show();
				}
			}
			else
				Log.d(ShowLeaderboardCallback.class.getSimpleName(),
						"Not in MainActivity context. Leaderboard was not launched.");
		}
	}

}
