package de.asteromania.doitlist.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import de.asteromania.doitlist.domain.DoItList;
import de.asteromania.doitlist.domain.DoItListItem;

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
		// TODO
		DoItList list = new DoItList(listName);

		SQLiteDatabase db = databaseHelper.getWritableDatabase();

	}

	@Override
	public List<DoItList> getEmptyLists()
	{
		// TODO
		List<DoItList> lists = new ArrayList<DoItList>();
		lists.add(new DoItList("Testliste 1"));
		lists.add(new DoItList("Testliste 2"));
		lists.add(new DoItList("Testliste 3"));
		return lists;
	}

	@Override
	public List<DoItListItem> getListItems(long listid)
	{
		// TODO
		List<DoItListItem> lists = new ArrayList<DoItListItem>();
		lists.add(new DoItListItem("Testitem 1"));
		lists.add(new DoItListItem("Testitem 2"));
		lists.add(new DoItListItem("Testitem 3"));
		return lists;
	}

	@Override
	public void deleteList(long listId)
	{
		// TODO
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
	}

	@Override
	public String getListName(long selectedListId)
	{
		// TODO Auto-generated method stub
		return "List Name";
	}

	@Override
	public DoItListItem getListItem(long selectedTaskId)
	{
		// TODO Auto-generated method stub
		return new DoItListItem("TestItem");
	}

	@Override
	public void updateListItem(DoItListItem doItListItem)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void updateListName(long selectedListId, String string)
	{
		// TODO Auto-generated method stub

	}

}
