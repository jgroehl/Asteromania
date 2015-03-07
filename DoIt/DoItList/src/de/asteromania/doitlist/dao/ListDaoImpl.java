package de.asteromania.doitlist.dao;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<DoItList> getEmptyLists()
	{
		List<DoItList> lists = new ArrayList<DoItList>();
		lists.add(new DoItList("Testliste 1"));
		lists.add(new DoItList("Testliste 2"));
		lists.add(new DoItList("Testliste 3"));
		return lists;
	}

}
