package de.asteromania.doitlist.dao.scheme;

import android.provider.BaseColumns;

public final class SchemeList implements BaseColumns
{
	public static final String TABLE_NAME = "list";
	public static final String COLUMN_NAME_ID = "listid";
	public static final String COLUMN_NAME_NAME = "listname";

	private SchemeList()
	{
	};
}
