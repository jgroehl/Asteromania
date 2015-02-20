package de.asteromania.doitlist.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.Date;

import android.content.Context;
import android.util.Log;

public class DataDaoImpl implements DataDao
{

	private static final String FILE_NAME_DATE = "currentDate";
	private static final String FILE_NAME_TASK_ID = "currentTaskId";
	private static final String TAG = DataDaoImpl.class.getSimpleName();
	private final Context context;

	public DataDaoImpl(Context context)
	{
		this.context = context;
	}

	@Override
	public Date getSelectedDate()
	{
		try
		{
			String read = readFromFile(FILE_NAME_DATE);
			if (read == null)
				return new Date();
			return TasksDao.DATE_FORMAT.parse(read);
		}
		catch (ParseException e)
		{
			return new Date();
		}
	}

	@Override
	public void setSelectedDate(Date date)
	{
		writeToFile(FILE_NAME_DATE, TasksDao.DATE_FORMAT.format(date));
	}

	@Override
	public long getSelectedTaskId()
	{
		String readFromFile = readFromFile(FILE_NAME_TASK_ID);
		if (readFromFile == null)
			return -1;
		return Long.parseLong(readFromFile);
	}

	@Override
	public void setSelectedTaskId(long id)
	{
		writeToFile(FILE_NAME_TASK_ID, String.valueOf(id));
	}

	private String readFromFile(String fileName)
	{
		String result = null;
		try
		{
			FileInputStream fis = context.openFileInput(fileName);
			if (fis == null)
			{
				Log.w(TAG, "When opening " + fileName + " the file was not existing.");
				return result;
			}
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(isr);

			String readLine = bufferedReader.readLine();
			if (readLine == null)
			{
				Log.w(TAG, "When reading " + fileName + " the file was not readable or empty.");
				return result;
			}
			result = readLine;

			fis.close();
			isr.close();
			bufferedReader.close();
		}
		catch (IOException e)
		{
			Log.e(TAG, "Error while retrieving input string from file: " + e.getMessage());
			return result;
		}
		return result;
	}

	private void writeToFile(String file, String data)
	{
		try
		{
			FileOutputStream fos = context.openFileOutput(file, Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(data);
			bw.close();
			osw.close();
			fos.close();
		}
		catch (IOException e)
		{
			Log.e(TAG, "Saving value " + data + " for " + file + " failed because of IO: " + e.getMessage());
		}
	}

}
