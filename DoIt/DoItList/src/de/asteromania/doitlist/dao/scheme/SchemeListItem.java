package de.asteromania.doitlist.dao.scheme;

import android.provider.BaseColumns;

public final class SchemeListItem implements BaseColumns
{
	public static final String TABLE_NAME = "listitem";
	public static final String COLUMN_NAME_ID = "itemid";
	public static final String COLUMN_NAME_TEXT = "itemtext";
	public static final String COLUMN_LIST_ID = "listid";

	private SchemeListItem()
	{
	};
}
