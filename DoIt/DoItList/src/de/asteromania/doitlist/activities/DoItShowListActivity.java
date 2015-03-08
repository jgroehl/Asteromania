package de.asteromania.doitlist.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.adapter.DoItListItemAdapter;
import de.asteromania.doitlist.intent.IntentHandler;
import de.asteromania.doitlist.intent.IntentHandler.Intent;

public class DoItShowListActivity extends AbstractDoItActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_list);
	}

	public void createClicked(View view)
	{
		IntentHandler.startIntent(Intent.CREATE_LIST_ITEM, this);
	}

	public void addClicked(View view)
	{
		// TODO
		finish();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		updateView();
	}

	private void updateView()
	{
		ListView listView = (ListView) findViewById(R.id.show_list_listview);
		DoItListItemAdapter adapter = new DoItListItemAdapter(getListDao().getListItems(
				getDataDao().getSelectedListId()), this);
		listView.setAdapter(adapter);
	}
}
