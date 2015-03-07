package de.asteromania.doitlist.dao;

import android.database.sqlite.SQLiteDatabase;
import de.asteromania.doitlist.domain.DoItList;

public class ListDaoImpl implements ListDao
{

	private static final String TAG = ListDaoImpl.class.getSimpleName();
	private final DatabaseHelper databaseHelper;

	public ListDaoImpl(DatabaseHelper databaseHelper)
	{
		this.databaseHelper = databaseHelper;
	}

	@Override
	public void createList(String listName)
	{
		DoItList list = new DoItList(listName);

		SQLiteDatabase db = databaseHelper.getWritableDatabase();

	}

}
