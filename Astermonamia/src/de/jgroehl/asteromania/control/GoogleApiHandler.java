package de.jgroehl.asteromania.control;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.callbacks.PurchaseItemCallback.PurchaseType;

public class GoogleApiHandler
{

	private final GoogleApiClient apiClient;
	private final Context context;

	public GoogleApiHandler(GoogleApiClient apiClient, Context context)
	{
		this.apiClient = apiClient;
		this.context = context;
	}

	public void addToLeaderBoard(long score)
	{
		if (!apiClient.isConnected())
		{
			return;
		}
		Games.Leaderboards.submitScore(apiClient, context.getResources().getString(R.string.leaderboard_highscore),
				score);
		checkForHighscoreAchievement(score);
	}

	private void checkForHighscoreAchievement(long score)
	{

		if (score >= 1000000)
		{
			Games.Achievements.unlock(apiClient,
					context.getResources().getString(R.string.achievement_nothing_to_do_all_day_long___));
		}
		else if (score >= 100000)
		{
			Games.Achievements.unlock(apiClient, context.getResources().getString(R.string.achievement_getting_pro_));
		}
		else if (score >= 10000)
		{
			Games.Achievements.unlock(apiClient,
					context.getResources().getString(R.string.achievement_this_is_what_im_talking_about_));
		}
		else if (score == 1337)
		{
			Games.Achievements.unlock(apiClient, context.getResources().getString(R.string.achievement_uber_leet));
		}
		else if (score >= 1000)
		{
			Games.Achievements.unlock(apiClient, context.getResources().getString(R.string.achievement_warmed_up_yet));
		}
		else if (score >= 100)
		{
			Games.Achievements.unlock(apiClient, context.getResources()
					.getString(R.string.achievement_getting_started_));
		}

	}

	public void unlockPurchaseAchievement(PurchaseType type)
	{

		if (!apiClient.isConnected())
			return;

		String achievement = "";

		switch (type)
		{
			case SHIELD_GENERATOR:
				achievement = context.getResources().getString(R.string.achievement_feeling_protected);
				break;
			case DOUBLE_SHOT:
				achievement = context.getResources().getString(R.string.achievement_double_your_deeps_);
				break;
			case TRIPLE_SHOT:
				achievement = context.getResources().getString(R.string.achievement_boom_boom_baby);
				break;
			default:
				return;
		}
		Games.Achievements.unlock(apiClient, achievement);

	}

	public GoogleApiClient getApiClient()
	{
		return apiClient;
	}

	public void checkForSpendingAchievement(long amount)
	{

		if (!apiClient.isConnected())
			return;

		try
		{
			String achievement = "";

			if (amount >= 1000000)
			{
				achievement = context.getResources().getString(R.string.achievement_millionaire);
			}
			else if (amount >= 250000)
			{
				achievement = context.getResources().getString(R.string.achievement_shopping_frenzy);
			}
			else if (amount >= 50000)
			{
				achievement = context.getResources().getString(R.string.achievement_being_on_a_shopping_binge);
			}
			else if (amount >= 1000)
			{
				achievement = context.getResources().getString(R.string.achievement_welcome_to_value_town_);
			}
			else if (amount >= 250)
			{
				achievement = context.getResources().getString(R.string.achievement_upgrade_time_);
			}
			else if (amount >= 1)
			{
				achievement = context.getResources().getString(R.string.achievement_shopping_time);
			}
			Games.Achievements.unlock(apiClient, achievement);
		}
		catch (Exception e)
		{
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	public void killedEnemy()
	{
		if (!apiClient.isConnected())
			return;
		Games.Events.increment(apiClient, context.getResources().getString(R.string.event_aliens_killed), 1);
	}

	public void destroyedAsteroid()
	{
		if (!apiClient.isConnected())
			return;
		Games.Events.increment(apiClient, context.getResources().getString(R.string.event_asteroids_destroyed), 1);
	}

	public void checkForHealthAchievement(int maximum)
	{
		if (!apiClient.isConnected())
			return;

		String achievement = "empty";
		if (maximum >= 250)
		{
			achievement = context.getResources().getString(R.string.achievement_indestructable);
		}
		else if (maximum >= 50)
		{
			achievement = context.getResources().getString(R.string.achievement_being_a_tank_);
		}
		else if (maximum >= 10)
		{
			achievement = context.getResources().getString(R.string.achievement_bulking_up);
		}
		Games.Achievements.unlock(apiClient, achievement);

	}

	public void checkForDamageAchievement(int bonusDamage)
	{
		if (!apiClient.isConnected())
			return;

		String achievement = "empty";
		if (bonusDamage >= 50)
		{
			achievement = context.getResources().getString(R.string.achievement_there_is_nothing_in_your_way);
		}
		else if (bonusDamage >= 15)
		{
			achievement = context.getResources().getString(R.string.achievement_feel_the_pain);
		}
		else if (bonusDamage >= 5)
		{
			achievement = context.getResources().getString(R.string.achievement_strength_is_power);
		}
		Games.Achievements.unlock(apiClient, achievement);

	}

}
