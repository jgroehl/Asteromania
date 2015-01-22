package de.jgroehl.api.net;

import android.os.AsyncTask;

public abstract class AbstractHttpTask extends AsyncTask<String, Void, String>
{

	protected static final String CLIENT_NAME = "jgroehl-Android-Client";

	public interface OnResponseCallback
	{
		void performTask(String result);
	}

}
