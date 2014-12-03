package de.jgroehl.asteromania.control.callbacks;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.interfaces.EventCallback;

public class SendMessageCallback implements EventCallback
{

	public static final String TARGET_WHATSAPP = "com.whatsapp";

	public interface GetText
	{
		String getText();
	}

	private final String text;
	private final String target;
	private final GetText getText;

	public SendMessageCallback(String text, String target)
	{
		this.text = text;
		this.target = target;
		getText = null;
	}

	public SendMessageCallback(GetText getText, String target)
	{
		this.getText = getText;
		this.target = target;
		text = null;
	}

	@Override
	public void action(BaseGameHandler gameHandler)
	{
		Intent textIntent = new Intent(Intent.ACTION_SEND);
		textIntent.setType("text/plain");
		textIntent.putExtra(Intent.EXTRA_TEXT, getText == null ? text : getText.getText());
		if (target != null && isInstalled(target, gameHandler))
			textIntent.setPackage(target);
		gameHandler.getContext().startActivity(
				Intent.createChooser(textIntent,
						gameHandler.getContext().getString(de.jgroehl.asteromania.R.string.share_with_friend)));
	}

	private boolean isInstalled(String target, BaseGameHandler gameHandler)
	{
		PackageManager pm = gameHandler.getContext().getPackageManager();
		try
		{
			pm.getPackageInfo(target, PackageManager.GET_ACTIVITIES);
			return true;
		}
		catch (NameNotFoundException e)
		{
			return false;
		}
	}

}
