package de.asteromania.doitlist.intent;

import android.content.Context;
import android.util.Log;

public final class IntentHandler
{

	public enum Intent
	{
		CREATE, UPDATE, LIST_MENU, CREATE_LIST, SHOW_LIST, CREATE_LIST_ITEM, UPDATE_LIST_ITEM, UPDATE_LIST_NAME;

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
		Log.d(IntentHandler.class.getSimpleName(), "Starting Intent " + i);
		android.content.Intent intent = new android.content.Intent(i.getAction());
		c.startActivity(intent);
	}

}
