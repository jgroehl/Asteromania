package de.asteromania.doitlist.activities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import de.asteromania.doitlist.domain.DoItTasks;

public class AbstractDoItActivity extends ActionBarActivity
{

	private static final String TAG = DoItMainActivity.class.getSimpleName();
	private static final String FILE_NAME = "tasks";
	private DoItTasks tasks;

	@Override
	protected void onStart()
	{
		Log.d(TAG, "On Start called.");
		super.onResume();
		tasks = readTasks();
	}

	@Override
	protected void onPause()
	{
		Log.d(TAG, "On Pause called.");
		writeTasks();
		super.onPause();
	}

	private void writeTasks()
	{
		FileOutputStream out;
		try
		{
			Log.d(TAG, "Writing tasks.");
			out = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			new Persister().write(tasks, out);
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getMessage());
		}
	}

	private DoItTasks readTasks()
	{
		FileInputStream fis = null;
		try
		{
			Log.d(TAG, "Reading tasks.");
			fis = openFileInput(FILE_NAME);
			return new Persister().read(DoItTasks.class, fis);
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getMessage());
			return new DoItTasks(null);
		}
	}

	public DoItTasks getDoItTasks()
	{
		return tasks;
	}

}
