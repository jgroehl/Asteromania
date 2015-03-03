package de.asteromania.doitlist.dao.scheme;

import android.provider.BaseColumns;

public final class SchemeTask implements BaseColumns
{
	public static final String TABLE_NAME = "task";
	public static final String COLUMN_NAME_ID = "taskid";
	public static final String COLUMN_NAME_TEXT = "text";
	public static final String COLUMN_NAME_DATE = "date";
	public static final String COLUMN_NAME_FINISHED = "finished";

	private SchemeTask()
	{
	};
}
