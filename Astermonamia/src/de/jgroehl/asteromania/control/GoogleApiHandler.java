package de.jgroehl.asteromania.control;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.callbacks.PurchaseItemCallback.PurchaseType;

public class GoogleApiHandler {

	private final GoogleApiClient apiClient;
	private final Context context;

	public GoogleApiHandler(GoogleApiClient apiClient, Context context) {
		this.apiClient = apiClient;
		this.context = context;
	}

	public void addToLeaderBoard(long score) {
		if (!apiClient.isConnected())
			return;
		Games.Leaderboards.submitScore(apiClient, context.getResources()
				.getString(R.string.leaderboard_highscore), score);
		checkForHighscoreAchievement(score);
	}

	private void checkForHighscoreAchievement(long score) {

		if (score >= 1000000) {
			Games.Achievements
					.unlock(apiClient,
							context.getResources()
									.getString(
											R.string.achievement_nothing_to_do_all_day_long___));
		} else if (score >= 100000) {
			Games.Achievements.unlock(apiClient, context.getResources()
					.getString(R.string.achievement_getting_pro_));
		} else if (score >= 10000) {
			Games.Achievements
					.unlock(apiClient,
							context.getResources()
									.getString(
											R.string.achievement_this_is_what_im_talking_about_));
		} else if (score == 1337) {
			Games.Achievements.unlock(apiClient, context.getResources()
					.getString(R.string.achievement_uber_leet));
		} else if (score >= 1000) {
			Games.Achievements.unlock(apiClient, context.getResources()
					.getString(R.string.achievement_warmed_up_yet));
		} else if (score >= 100) {
			Games.Achievements.unlock(apiClient, context.getResources()
					.getString(R.string.achievement_getting_started_));
		}

	}

	public void unlockPurchase(PurchaseType type) {

		if (!apiClient.isConnected())
			return;

		String achievement = "";

		switch (type) {
		case SHIELD_GENERATOR:
			context.getResources().getString(
					R.string.achievement_feeling_protected);
			break;
		case DOUBLE_SHOT:
			context.getResources().getString(
					R.string.achievement_double_your_deeps_);
			break;
		case TRIPLE_SHOT:
			context.getResources().getString(
					R.string.achievement_boom_boom_baby);
			break;
		default:
			return;
		}
		Games.Achievements.unlock(apiClient, achievement);

	}

}
