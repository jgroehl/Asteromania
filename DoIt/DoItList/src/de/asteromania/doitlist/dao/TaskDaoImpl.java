package de.asteromania.doitlist.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import de.asteromania.doitlist.dao.scheme.SchemeTask;
import de.asteromania.doitlist.domain.DoItListItem;
import de.asteromania.doitlist.domain.DoItTask;

public class TaskDaoImpl implements TaskDao
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
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor c = null;
		if (date != null)
		{
			String datetime = DATE_FORMAT.format(date);
			c = db.rawQuery("SELECT * FROM " + SchemeTask.TABLE_NAME + " WHERE " + SchemeTask.COLUMN_NAME_DATE
					+ " = (?)", new String[] { datetime });
		}
		else
		{
			c = db.rawQuery("SELECT * FROM " + SchemeTask.TABLE_NAME, new String[] {});
		}

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
				Log.w(TAG, "Error parsing date from " + c.getString(2) + " reverting to given date.");
				nuDate = date;
			}

			tasks.add(new DoItTask(id, text, nuDate, finished));
		}

		return tasks;
	}

	@Override
	public void deleteTasks(Collection<? extends DoItTask> tasks)
	{
		for (DoItTask task : tasks)
		{
			SQLiteDatabase db = databaseHelper.getWritableDatabase();
			db.delete(SchemeTask.TABLE_NAME, SchemeTask.COLUMN_NAME_ID + " = (?)", new String[] { "" + task.getId() });
		}
	}

	@Override
	public void updateTask(DoItTask task)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		int rows = db.update(SchemeTask.TABLE_NAME, createContentValuesFromTask(task), SchemeTask.COLUMN_NAME_ID
				+ " = (?)", new String[] { "" + task.getId() });

		Log.d(TAG, "Updated rows: " + rows);
	}

	@Override
	public void storeTask(DoItTask task)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		db.insert(SchemeTask.TABLE_NAME, null, createContentValuesFromTask(task));
	}

	private ContentValues createContentValuesFromTask(DoItTask task)
	{
		ContentValues values = new ContentValues();
		values.put(SchemeTask.COLUMN_NAME_ID, task.getId());
		values.put(SchemeTask.COLUMN_NAME_TEXT, task.getText());
		values.put(SchemeTask.COLUMN_NAME_DATE, DATE_FORMAT.format(task.getDate()));
		values.put(SchemeTask.COLUMN_NAME_FINISHED, String.valueOf(task.isFinished()));
		return values;
	}

	@Override
	public DoItTask getTask(long selectedTaskId)
	{
		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM " + SchemeTask.TABLE_NAME + " WHERE " + SchemeTask.COLUMN_NAME_ID
				+ " = (?)", new String[] { "" + selectedTaskId });

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

	@Override
	public void createTasks(Collection<? extends DoItListItem> values, Date selectedDate)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		for (DoItListItem i : values)
			db.insert(SchemeTask.TABLE_NAME, null, createContentValuesFromTask(new DoItTask(i.getText(), selectedDate)));

	}
}
