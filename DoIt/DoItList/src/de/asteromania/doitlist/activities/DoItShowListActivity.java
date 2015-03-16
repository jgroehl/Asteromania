package de.asteromania.doitlist.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.adapter.DoItListItemAdapter;
import de.asteromania.doitlist.intent.IntentHandler;
import de.asteromania.doitlist.intent.IntentHandler.Intent;

public class DoItShowListActivity extends AbstractDoItActivity
{
	
	private DoItListItemAdapter adapter;

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

	public void deleteListClicked(View view)
	{
		getListDao().deleteList(getDataDao().getSelectedListId());
		finish();
	}

	public void deleteItemsClicked(View view)
	{
		getListDao().deleteItems(adapter.getSelectedItems());
	}

	public void registerItemsClicked(View view)
	{
		getTaskDao().createTasks(adapter.getValues(), getDataDao().getSelectedDate());
		finish();
	}

	public void editNameClicked(View view)
	{
		IntentHandler.startIntent(Intent.UPDATE_LIST_NAME, this);
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
		long selectedListId = getDataDao().getSelectedListId();
		adapter = new DoItListItemAdapter(getListDao().getListItems(selectedListId), this);
		listView.setAdapter(adapter);

		TextView textView = (TextView) findViewById(R.id.textview_show_list);
		textView.setText(getListDao().getListName(selectedListId));
	}
}
