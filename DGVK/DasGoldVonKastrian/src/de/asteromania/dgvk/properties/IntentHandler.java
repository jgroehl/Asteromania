package de.asteromania.dgvk.properties;

import android.content.Context;

public final class IntentHandler
{

	public enum Intent
	{
		LOGIN, REGISTER;

		private final String action;

		private Intent()
		{
			this.action = "de.asteromania.dgvk." + toString();
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
