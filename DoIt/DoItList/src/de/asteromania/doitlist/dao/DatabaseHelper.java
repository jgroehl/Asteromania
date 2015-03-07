package de.asteromania.doitlist.dao;

import de.asteromania.doitlist.dao.scheme.SchemeList;
import de.asteromania.doitlist.dao.scheme.SchemeListItem;
import de.asteromania.doitlist.dao.scheme.SchemeTask;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{

	private static final String SQL_CREATE_TABLE_TASK = "CREATE TABLE " + SchemeTask.TABLE_NAME + " ("
			+ SchemeTask.COLUMN_NAME_ID + " LONG PRIMARY KEY," + SchemeTask.COLUMN_NAME_TEXT + " TEXT" + ","
			+ SchemeTask.COLUMN_NAME_DATE + " TEXT" + "," + SchemeTask.COLUMN_NAME_FINISHED + " TEXT" + " )";

	private static final String SQL_CREATE_TABLE_LIST = "CREATE TABLE " + SchemeList.TABLE_NAME + "("
			+ SchemeList.COLUMN_NAME_ID + " LONG PRIMARY KEY, " + SchemeList.COLUMN_NAME_NAME + " TEXT)";

	private static final String SQL_CREATE_TABLE_LIST_ITEM = "CREATE TABLE " + SchemeListItem.TABLE_NAME + "("
			+ SchemeListItem.COLUMN_NAME_ID + " LONG PRIMARY KEY, " + SchemeListItem.COLUMN_NAME_TEXT + " TEXT, "
			+ SchemeListItem.COLUMN_LIST_ID + " LONG, " + "FOREIGN KEY (" + SchemeListItem.COLUMN_LIST_ID
			+ ") REFERENCES " + SchemeList.TABLE_NAME + " (" + SchemeList.COLUMN_NAME_ID + ") ON DELETE CASCADE)";

	private static final String SQL_DELETE_TASKS = "DROP TABLE IF EXISTS " + SchemeTask.TABLE_NAME;
	private static final String SQL_DELETE_LIST_ITEMS = "DROP TABLE IF EXISTS " + SchemeListItem.TABLE_NAME;
	private static final String SQL_DELETE_LISTS = "DROP TABLE IF EXISTS " + SchemeList.TABLE_NAME;

	public static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "DoItTasks.db";

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(SQL_CREATE_TABLE_TASK);
		db.execSQL(SQL_CREATE_TABLE_LIST);
		db.execSQL(SQL_CREATE_TABLE_LIST_ITEM);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL(SQL_DELETE_TASKS);
		db.execSQL(SQL_DELETE_LIST_ITEMS);
		db.execSQL(SQL_DELETE_LISTS);
		onCreate(db);
	}
}
