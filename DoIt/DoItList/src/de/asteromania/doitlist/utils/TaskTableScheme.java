package de.asteromania.doitlist.utils;

import android.provider.BaseColumns;

public final class TaskTableScheme implements BaseColumns
{
	public static final String TABLE_NAME = "task";
	public static final String COLUMN_NAME_ID = "taskid";
	public static final String COLUMN_NAME_TEXT = "text";
	public static final String COLUMN_NAME_DATE = "date";
	public static final String COLUMN_NAME_FINISHED = "finished";

	private TaskTableScheme()
	{
	};
}
