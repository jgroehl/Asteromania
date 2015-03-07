package de.asteromania.doitlist.activities;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.widget.DatePicker;
import de.asteromania.doitlist.dao.DataDao;
import de.asteromania.doitlist.dao.DataDaoImpl;
import de.asteromania.doitlist.dao.DatabaseHelper;
import de.asteromania.doitlist.dao.ListDao;
import de.asteromania.doitlist.dao.ListDaoImpl;
import de.asteromania.doitlist.dao.TaskDaoImpl;
import de.asteromania.doitlist.dao.TaskDao;

public class AbstractDoItActivity extends Activity
{

	private final TaskDao tasksDao;
	private final DataDao dataDao;
	private final ListDao listDao;

	public AbstractDoItActivity()
	{
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		tasksDao = new TaskDaoImpl(databaseHelper);
		dataDao = new DataDaoImpl(this);
		listDao = new ListDaoImpl(databaseHelper);
	}

	public TaskDao getDatabase()
	{
		return tasksDao;
	}

	public DataDao getDataDao()
	{
		return dataDao;
	}

	public ListDao getListDao()
	{
		return listDao;
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
