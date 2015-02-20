package de.asteromania.doitlist.intent;

import android.content.Context;

public final class IntentHandler
{

	public enum Intent
	{
		CREATE, UPDATE;

		private final String action;

		private Intent()
		{
			this.action = "de.asteromania.doitlist." + toString();
		}

		public String getAction()
		{
			return action;
		}

	}

	private IntentHandler()
	{

	}

	public static void startIntent(Intent i, Context c)
	{
		c.startActivity(new android.content.Intent(i.getAction()));
	}

}
