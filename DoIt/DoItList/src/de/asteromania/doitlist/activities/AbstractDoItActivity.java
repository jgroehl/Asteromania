package de.asteromania.doitlist.activities;

import android.app.Activity;
import de.asteromania.doitlist.utils.DataDao;
import de.asteromania.doitlist.utils.DataDaoImpl;
import de.asteromania.doitlist.utils.DatabaseHelper;
import de.asteromania.doitlist.utils.TaskDaoImpl;
import de.asteromania.doitlist.utils.TasksDao;

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

}
