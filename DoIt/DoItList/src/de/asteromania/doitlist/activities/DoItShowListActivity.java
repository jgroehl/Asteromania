package de.asteromania.doitlist.activities;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.adapter.DoItListItemAdapter;
import de.asteromania.doitlist.domain.DoItListItem;
import de.asteromania.doitlist.intent.IntentHandler;
import de.asteromania.doitlist.intent.IntentHandler.Intent;

public class DoItShowListActivity extends AbstractDoItActivity
{
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM", Locale.getDefault());

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

	public void deleteItemsClicked(View view)
	{
		getListDao().deleteItems(adapter.getSelectedItems());
	}

	public void registerItemsClicked(View view)
	{
		Collection<DoItListItem> selectedItems = adapter.getSelectedItems();
		if (selectedItems.size() > 0)
		{
			getTaskDao().createTasks(selectedItems, getDataDao().getSelectedDate());
			Toast.makeText(this,
					getString(R.string.show_list_added) + " " + dateFormat.format(getDataDao().getSelectedDate()),
					Toast.LENGTH_LONG).show();
		}
		finish();
	}

	public void editNameClicked(View view)
	{
		IntentHandler.startIntent(Intent.UPDATE_LIST_NAME, this);
	}

	public void selectAllClicked(View view)
	{
		boolean allSelected = adapter.allItemsSelected();
		for (DoItListItem t : adapter.getValues())
			t.setSelected(!allSelected);
		adapter.selectAll(!allSelected);
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
		List<DoItListItem> items = getListDao().getListItems(selectedListId);
		if (items == null)
			finish();
		adapter = new DoItListItemAdapter(items, this);
		listView.setAdapter(adapter);

		TextView textView = (TextView) findViewById(R.id.textview_show_list);
		String listName = getListDao().getListName(selectedListId);
		if (listName == null || listName.trim().isEmpty())
			finish();
		textView.setText(listName);
	}
}
