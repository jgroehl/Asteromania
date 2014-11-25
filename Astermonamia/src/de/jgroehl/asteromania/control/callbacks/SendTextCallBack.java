package de.jgroehl.asteromania.control.callbacks;

import android.content.Intent;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.interfaces.EventCallback;

public class SendTextCallBack implements EventCallback
{

	public interface GetText
	{
		String getText();
	}

	private final String text;
	private final GetText getText;

	public SendTextCallBack(String text)
	{
		this.text = text;
		getText = null;
	}

	public SendTextCallBack(GetText getText)
	{
		this.getText = getText;
		text = null;
	}

	@Override
	public void action(BaseGameHandler gameHandler)
	{
		Intent textIntent = new Intent(Intent.ACTION_SEND);
		textIntent.setType("text/plain");
		textIntent.putExtra(Intent.EXTRA_TEXT, getText == null ? text : getText.getText());
		gameHandler.getContext().startActivity(
				Intent.createChooser(textIntent,
						gameHandler.getContext().getString(de.jgroehl.asteromania.R.string.share_with_friend)));
	}

}
