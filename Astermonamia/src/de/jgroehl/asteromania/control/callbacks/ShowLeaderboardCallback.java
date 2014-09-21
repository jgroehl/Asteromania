package de.jgroehl.asteromania.control.callbacks;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.games.Games;

import de.jgroehl.asteromania.MainActivity;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.interfaces.EventCallback;

public class ShowLeaderboardCallback implements EventCallback {

	private static final int LEADERBOARD_REQUEST = 101;

	@Override
	public void action(GameHandler gameHandler) {
		if (gameHandler.getContext() instanceof MainActivity) {
			if (gameHandler.getApiHandler().getApiClient().isConnected()) {
				((MainActivity) gameHandler.getContext())
						.startActivityForResult(
								Games.Leaderboards
										.getLeaderboardIntent(
												gameHandler.getApiHandler()
														.getApiClient(),
												gameHandler
														.getContext()
														.getResources()
														.getString(
																R.string.leaderboard_highscore)),
								LEADERBOARD_REQUEST);
			} else {
				Toast.makeText(gameHandler.getContext(),
						R.string.no_connection_to_play_services,
						Toast.LENGTH_LONG).show();
			}
		} else
			Log.d(ShowLeaderboardCallback.class.getSimpleName(),
					"Not in MainActivity context. Leaderboard was not launched.");
	}

}
