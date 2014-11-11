package de.jgroehl.asteromania.control.callbacks;

import android.content.Intent;
import android.net.Uri;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;

public class OpenWebsiteCallback implements EventCallback
{

	private static final String HTTP = "http://";
	private static final String HTTPS = "https://";
	private final String url;

	public OpenWebsiteCallback(String url)
	{
		if (url == null)
			throw new NullPointerException("Url was null.");
		if (!url.startsWith(HTTP) && !url.startsWith(HTTPS))
			url = HTTP + url;

		this.url = url;

	}

	@Override
	public void action(BaseGameHandler gameHandler)
	{
		if (gameHandler instanceof AsteromaniaGameHandler)
		{
			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;
			Intent urlIntent = new Intent(Intent.ACTION_VIEW);
			urlIntent.setData(Uri.parse(url));
			asteromaniaGameHandler.getContext().startActivity(urlIntent);
		}

	}

}
