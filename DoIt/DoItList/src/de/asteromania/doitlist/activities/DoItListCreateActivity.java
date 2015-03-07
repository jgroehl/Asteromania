package de.asteromania.doitlist.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import de.asteromania.doitlist.R;

public class DoItListCreateActivity extends AbstractDoItActivity
{

	private EditText listNameEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_list);

		listNameEditText = (EditText) findViewById(R.id.edittext_create_list);
	}

	public void okayClicked(View view)
	{
		String listName = listNameEditText.getText().toString();
		getListDao().createList(listName);
		finish();
	}
}
