package de.asteromania.doitlist.activities;

import java.util.Iterator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.adapter.DoItAdapter;
import de.asteromania.doitlist.domain.DoItTask;
import de.asteromania.doitlist.intent.IntentHandler;
import de.asteromania.doitlist.intent.IntentHandler.Intent;

public class DoItMainActivity extends AbstractDoItActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_do_it_main);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		ListView listView = (ListView) findViewById(R.id.todo_listview);
		DoItAdapter adapter = new DoItAdapter(getDoItTasks().getTasks(), this);
		listView.setAdapter(adapter);
	}

	@Override
	protected void onDestroy()
	{
		Iterator<DoItTask> it = getDoItTasks().getTasks().iterator();
		while (it.hasNext())
			if (it.next().isFinished())
				it.remove();
		writeTasks();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.do_it_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_quit)
		{
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void createTask(View view)
	{
		IntentHandler.startIntent(Intent.CREATE, this);
	}
}
