package de.asteromania.doitlist.activities;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.widget.DatePicker;
import de.asteromania.doitlist.dao.DataDao;
import de.asteromania.doitlist.dao.DataDaoImpl;
import de.asteromania.doitlist.dao.DatabaseHelper;
import de.asteromania.doitlist.dao.TaskDaoImpl;
import de.asteromania.doitlist.dao.TasksDao;

public class AbstractDoItActivity extends Activity
{

	private final TasksDao tasksDao;
	private final DataDao dataDao;

	public AbstractDoItActivity()
	{
		tasksDao = new TaskDaoImpl(new DatabaseHelper(this));
		dataDao = new DataDaoImpl(this);
	}

	public TasksDao getDatabase()
	{
		return tasksDao;
	}

	public DataDao getDataDao()
	{
		return dataDao;
	}

	public Date getDateFromDatePicker(DatePicker datePicker)
	{
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year = datePicker.getYear();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);

		return calendar.getTime();
	}
}
