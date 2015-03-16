package de.asteromania.doitlist.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.asteromania.doitlist.dao.scheme.SchemeList;
import de.asteromania.doitlist.dao.scheme.SchemeTask;
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
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		db.insert(SchemeList.TABLE_NAME, null, createContentValuesFromList(new DoItList(listName)));
	}

	private ContentValues createContentValuesFromList(DoItList list)
	{
		ContentValues values = new ContentValues();
		values.put(SchemeList.COLUMN_NAME_ID, list.getId());
		values.put(SchemeList.COLUMN_NAME_NAME, list.getName());
		return values;
	}

	@Override
	public List<DoItList> getEmptyLists()
	{
		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT " + SchemeList.COLUMN_NAME_NAME + " FROM " + SchemeList.TABLE_NAME, null);

		List<DoItList> lists = new ArrayList<DoItList>();

		if (c.moveToNext())
		{
			lists.add(new DoItList(c.getString(1)));
		}

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
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.delete(SchemeList.TABLE_NAME, SchemeTask.COLUMN_NAME_ID + " = (?)", new String[] { String.valueOf(listId) });
	}

	@Override
	public String getListName(long selectedListId)
	{
		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT " + SchemeList.COLUMN_NAME_NAME + " FROM " + SchemeList.TABLE_NAME + " WHERE "
				+ SchemeList.COLUMN_NAME_ID + " = (?)", new String[] { String.valueOf(selectedListId) });

		if (c.moveToNext())
		{
			return c.getString(1);
		}
		else
			return null;
	}

	@Override
	public DoItListItem getListItem(long selectedListId)
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

	@Override
	public void deleteItems(Collection<DoItListItem> selectedItems)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createListItem(String itemName)
	{
		// TODO Auto-generated method stub
	}

}
