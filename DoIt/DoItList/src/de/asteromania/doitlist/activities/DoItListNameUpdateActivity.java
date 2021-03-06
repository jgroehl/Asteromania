package de.asteromania.doitlist.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.intent.IntentHandler;
import de.asteromania.doitlist.intent.IntentHandler.Intent;

public class DoItListNameUpdateActivity extends AbstractDoItActivity
{

	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_list_name);

		editText = (EditText) findViewById(R.id.edittext_update_list_name);

		editText.setText(getListDao().getListName(getDataDao().getSelectedListId()));
	}

	public void okayClicked(View view)
	{
		if (!editText.getText().toString().trim().isEmpty())
		{
			getListDao().updateListName(getDataDao().getSelectedListId(), editText.getText().toString());
		}
		finish();
	}
	
	public void deleteListClicked(View view)
	{
		getListDao().deleteList(getDataDao().getSelectedListId());
		finish();
		IntentHandler.startIntent(Intent.LIST_MENU, this);
	}

}
