package de.asteromania.doitlist.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.adapter.DoItTaskAdapter;
import de.asteromania.doitlist.domain.DoItTask;
import de.asteromania.doitlist.intent.IntentHandler;
import de.asteromania.doitlist.intent.IntentHandler.Intent;
import de.asteromania.doitlist.utils.TasksDao;

public class DoItMainActivity extends AbstractDoItActivity
{

	private static final long DAY_MILLISECS = 86400000L;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_do_it_main);
		getDataDao().setSelectedDate(new Date());
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		updateView();
	}

	private void updateView()
	{
		ListView listView = (ListView) findViewById(R.id.todo_listview);
		Date selectedDate = getDataDao().getSelectedDate();
		DoItTaskAdapter adapter = new DoItTaskAdapter(getDatabase().getTasks(selectedDate), this);
		listView.setAdapter(adapter);

		TextView textView = (TextView) findViewById(R.id.textview_main_date);
		String dateString = TasksDao.DATE_FORMAT.format(selectedDate);
		textView.setText(dateString.equals(TasksDao.DATE_FORMAT.format(new Date())) ? "HEUTE" : dateString);
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

	public void deleteTasks(View view)
	{
		List<DoItTask> selectedTasks = new ArrayList<DoItTask>();
		for (DoItTask task : getDatabase().getTasks(getDataDao().getSelectedDate()))
		{
			if (task.isFinished())
				selectedTasks.add(task);
		}
		if (selectedTasks.size() > 0)
		{
			getDatabase().deleteTasks(selectedTasks);
			updateView();
		}
	}

	public void nextDay(View view)
	{
		getDataDao().setSelectedDate(new Date(getDataDao().getSelectedDate().getTime() + DAY_MILLISECS));
		updateView();
	}

	public void previousDay(View view)
	{
		getDataDao().setSelectedDate(new Date(getDataDao().getSelectedDate().getTime() - DAY_MILLISECS));
		updateView();
	}
}
