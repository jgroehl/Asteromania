package de.asteromania.doitlist.activities;

import java.util.ArrayList;
import java.util.Calendar;
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
import de.asteromania.doitlist.dao.TaskDao;
import de.asteromania.doitlist.domain.DoItTask;
import de.asteromania.doitlist.intent.IntentHandler;
import de.asteromania.doitlist.intent.IntentHandler.Intent;

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
		DoItTaskAdapter adapter = new DoItTaskAdapter(getTaskDao().getTasks(selectedDate), this);
		listView.setAdapter(adapter);

		TextView textView = (TextView) findViewById(R.id.textview_main_date);
		String dateString = TaskDao.DATE_FORMAT.format(selectedDate);
		dateString = (dateString.equals(TaskDao.DATE_FORMAT.format(new Date())) ? getString(R.string.today) + "\n"
				: dateString);
		Calendar c = Calendar.getInstance();
		c.setTime(selectedDate);

		textView.setText(dateString + calcWeekday(c.get(Calendar.DAY_OF_WEEK)));
	}

	private String calcWeekday(int day)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" (");
		switch (day)
		{
			case Calendar.MONDAY:
				sb.append(getString(R.string.monday));
				break;
			case Calendar.TUESDAY:
				sb.append(getString(R.string.tuesday));
				break;
			case Calendar.WEDNESDAY:
				sb.append(getString(R.string.wednesday));
				break;
			case Calendar.THURSDAY:
				sb.append(getString(R.string.thursday));
				break;
			case Calendar.FRIDAY:
				sb.append(getString(R.string.friday));
				break;
			case Calendar.SATURDAY:
				sb.append(getString(R.string.saturday));
				break;
			case Calendar.SUNDAY:
				sb.append(getString(R.string.sunday));
				break;
		}
		sb.append(")");
		return sb.toString();
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
		switch (id)
		{
			case R.id.action_quit:
				finish();
				return true;
			case R.id.action_select_all:
				selectAll(true);
				updateView();
				return true;
			case R.id.action_select_none:
				selectAll(false);
				updateView();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void selectAll(boolean select)
	{
		for (DoItTask t : getTaskDao().getTasks(getDataDao().getSelectedDate()))
		{
			t.setFinished(select);
			getTaskDao().updateTask(t);
		}
	}

	public void createTask(View view)
	{
		IntentHandler.startIntent(Intent.CREATE, this);
	}

	public void deleteTasks(View view)
	{
		List<DoItTask> selectedTasks = new ArrayList<DoItTask>();
		for (DoItTask task : getTaskDao().getTasks(getDataDao().getSelectedDate()))
		{
			if (task.isFinished())
				selectedTasks.add(task);
		}
		if (selectedTasks.size() > 0)
		{
			getTaskDao().deleteTasks(selectedTasks);
			updateView();
		}
	}

	public void openListMenu(View view)
	{
		IntentHandler.startIntent(Intent.LIST_MENU, this);
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
