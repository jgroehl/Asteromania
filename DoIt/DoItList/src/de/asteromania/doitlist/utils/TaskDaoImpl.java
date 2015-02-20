package de.asteromania.doitlist.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import de.asteromania.doitlist.domain.DoItTask;

public class TaskDaoImpl implements TasksDao
{

	private static final String TAG = TaskDaoImpl.class.getSimpleName();
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
			boolean finished = Boolean.valueOf(c.getString(3));
			Date nuDate;
			try
			{
				nuDate = DATE_FORMAT.parse(c.getString(2));
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
		for (DoItTask task : tasks)
		{
			SQLiteDatabase db = databaseHelper.getWritableDatabase();
			db.delete(TaskTableScheme.TABLE_NAME, TaskTableScheme.COLUMN_NAME_ID + " = (?)",
					new String[] { "" + task.getId() });
		}
	}

	@Override
	public void updateTask(DoItTask task)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		int rows = db.update(TaskTableScheme.TABLE_NAME, createContentValuesFromTask(task),
				TaskTableScheme.COLUMN_NAME_ID + " = (?)", new String[] { "" + task.getId() });

		Log.d(TAG, "Updated rows: " + rows);
	}

	@Override
	public void storeTask(DoItTask task)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		db.insert(TaskTableScheme.TABLE_NAME, null, createContentValuesFromTask(task));
	}

	private ContentValues createContentValuesFromTask(DoItTask task)
	{
		ContentValues values = new ContentValues();
		values.put(TaskTableScheme.COLUMN_NAME_ID, task.getId());
		values.put(TaskTableScheme.COLUMN_NAME_TEXT, task.getText());
		values.put(TaskTableScheme.COLUMN_NAME_DATE, DATE_FORMAT.format(task.getDate()));
		values.put(TaskTableScheme.COLUMN_NAME_FINISHED, String.valueOf(task.isFinished()));
		return values;
	}

	@Override
	public DoItTask getTask(long selectedTaskId)
	{
		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM " + TaskTableScheme.TABLE_NAME + " WHERE "
				+ TaskTableScheme.COLUMN_NAME_ID + " = (?)", new String[] { "" + selectedTaskId });

		if (c.moveToNext())
		{
			long id = c.getLong(0);
			String text = c.getString(1);
			boolean finished = Boolean.valueOf(c.getString(3));
			Date nuDate;
			try
			{
				nuDate = DATE_FORMAT.parse(c.getString(2));
			}
			catch (ParseException e)
			{
				nuDate = new Date();
			}

			DoItTask task = new DoItTask(id, text, nuDate, finished);

			return task;
		}
		else
		{
			return null;
		}

	}
}
