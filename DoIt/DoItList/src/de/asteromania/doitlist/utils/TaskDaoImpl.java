package de.asteromania.doitlist.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.asteromania.doitlist.domain.DoItTask;

public class TaskDaoImpl implements TasksDao
{

	private final DatabaseHelper databaseHelper;

	public TaskDaoImpl(DatabaseHelper databaseHelper)
	{
		this.databaseHelper = databaseHelper;
	}

	@Override
	public List<DoItTask> getTasks(Date date)
	{
		String datetime = DATE_FORMAT.format(date);

		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM " + TaskTableScheme.TABLE_NAME + " WHERE "
				+ TaskTableScheme.COLUMN_NAME_DATE + " = (?)", new String[] { datetime });

		List<DoItTask> tasks = new ArrayList<DoItTask>();

		while (c.moveToNext())
		{
			long id = c.getLong(0);
			String text = c.getString(1);
			boolean finished = Boolean.valueOf(c.getString(2));
			Date nuDate;
			try
			{
				nuDate = DATE_FORMAT.parse(c.getString(3));
			}
			catch (ParseException e)
			{
				nuDate = date;
			}

			tasks.add(new DoItTask(id, text, nuDate, finished));
		}

		return tasks;
	}

	@Override
	public void deleteTasks(List<DoItTask> tasks)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTask(DoItTask task)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void storeTask(DoItTask task)
	{
		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		ContentValues values = new ContentValues();
		values.put(TaskTableScheme.COLUMN_NAME_ID, task.getId());
		values.put(TaskTableScheme.COLUMN_NAME_TEXT, task.getText());
		values.put(TaskTableScheme.COLUMN_NAME_DATE, DATE_FORMAT.format(task.getDate()));
		values.put(TaskTableScheme.COLUMN_NAME_FINISHED, String.valueOf(task.isFinished()));

		db.insert(TaskTableScheme.TABLE_NAME, null, values);
	}

}
