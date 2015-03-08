package de.asteromania.doitlist.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import de.asteromania.doitlist.R;

public class DoItListItemCreateActivity extends AbstractDoItActivity
{

	private EditText itemNameEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_list_item);

		itemNameEditText = (EditText) findViewById(R.id.edittext_create_list_item);
	}

	public void okayClicked(View view)
	{
		String listName = itemNameEditText.getText().toString();
		getListDao().createList(listName);
		finish();
	}
}
