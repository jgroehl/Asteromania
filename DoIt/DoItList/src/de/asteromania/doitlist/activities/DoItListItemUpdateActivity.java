package de.asteromania.doitlist.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.domain.DoItListItem;

public class DoItListItemUpdateActivity extends AbstractDoItActivity
{

	private EditText editText;
	private DoItListItem doItListItem;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_list_item);

		editText = (EditText) findViewById(R.id.edittext_update_list_item);

		doItListItem = getListDao().getListItem(getDataDao().getSelectedListItem());

		if (doItListItem == null)
			finish();
		else
			editText.setText(doItListItem.getText());
	}

	public void okayClicked(View view)
	{
		if (!editText.getText().toString().trim().isEmpty())
		{
			doItListItem.setText(editText.getText().toString());
			getListDao().updateListItem(doItListItem);
		}
		finish();
	}

}
