package de.asteromania.doitlist.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{

	private static final String SQL_CREATE_TABLE_TASK = "CREATE TABLE " + TaskTableScheme.TABLE_NAME + " ("
			+ TaskTableScheme.COLUMN_NAME_ID + " LONG PRIMARY KEY," + TaskTableScheme.COLUMN_NAME_TEXT + " TEXT"
			+ "," + TaskTableScheme.COLUMN_NAME_DATE + " TEXT" + "," + TaskTableScheme.COLUMN_NAME_FINISHED + " TEXT"
			+ " )";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TaskTableScheme.TABLE_NAME;

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "DoItTasks.db";

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(SQL_CREATE_TABLE_TASK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
}
