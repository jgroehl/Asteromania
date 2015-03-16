package de.asteromania.doitlist.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.asteromania.doitlist.dao.scheme.SchemeList;
import de.asteromania.doitlist.dao.scheme.SchemeListItem;
import de.asteromania.doitlist.dao.scheme.SchemeTask;
import de.asteromania.doitlist.domain.DoItList;
import de.asteromania.doitlist.domain.DoItListItem;

public class ListDaoImpl implements ListDao
{

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

		Cursor c = db.rawQuery("SELECT " + SchemeList.COLUMN_NAME_NAME + " FROM " + SchemeList.TABLE_NAME,
				new String[] {});

		List<DoItList> lists = new ArrayList<DoItList>();

		while (c.moveToNext())
			lists.add(new DoItList(c.getString(0)));

		return lists;
	}

	@Override
	public List<DoItListItem> getListItems(long listid)
	{
		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT " + SchemeListItem.COLUMN_NAME_ID + ", " + SchemeListItem.COLUMN_NAME_TEXT
				+ " FROM " + SchemeListItem.TABLE_NAME + " WHERE " + SchemeListItem.COLUMN_LIST_ID + " = ?",
				new String[] { String.valueOf(listid) });

		List<DoItListItem> list = new ArrayList<DoItListItem>();

		while (c.moveToNext())
		{
			list.add(new DoItListItem(c.getLong(0), c.getString(1)));
		}
		return list;
	}

	@Override
	public void deleteList(long listId)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.delete(SchemeList.TABLE_NAME, SchemeList.COLUMN_NAME_ID + " = (?)", new String[] { String.valueOf(listId) });
	}

	@Override
	public String getListName(long selectedListId)
	{
		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT " + SchemeList.COLUMN_NAME_NAME + " FROM " + SchemeList.TABLE_NAME + " WHERE "
				+ SchemeList.COLUMN_NAME_ID + " = (?)", new String[] { String.valueOf(selectedListId) });

		if (c.moveToNext())
		{
			return c.getString(0);
		}
		else
			return null;
	}

	@Override
	public DoItListItem getListItem(long selectedListItemId)
	{
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT " + SchemeListItem.COLUMN_NAME_ID + ", " + SchemeListItem.COLUMN_NAME_TEXT
				+ " FROM " + SchemeListItem.TABLE_NAME + " WHERE " + SchemeListItem.COLUMN_NAME_ID + " = (?)",
				new String[] { String.valueOf(selectedListItemId) });

		if (c.moveToNext())
		{
			return new DoItListItem(c.getLong(0), c.getString(1));
		}
		else
			return null;
	}

	@Override
	public void updateListItem(DoItListItem doItListItem, long listId)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.update(SchemeListItem.TABLE_NAME, createContentValuesFromListItem(doItListItem, listId),
				SchemeListItem.COLUMN_NAME_ID + "= ?", new String[] { String.valueOf(doItListItem.getId()) });
	}

	@Override
	public void updateListName(long selectedListId, String string)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.update(SchemeList.TABLE_NAME, createContentValuesFromList(new DoItList(selectedListId, string)),
				SchemeList.COLUMN_NAME_ID + "= ?", new String[] { String.valueOf(selectedListId) });
	}

	@Override
	public void deleteItems(Collection<DoItListItem> selectedItems)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		for (DoItListItem item : selectedItems)
			db.delete(SchemeListItem.TABLE_NAME, SchemeListItem.COLUMN_NAME_ID + " = ?",
					new String[] { String.valueOf(item.getId()) });
	}

	@Override
	public void createListItem(String itemName, long listId)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.insert(SchemeListItem.TABLE_NAME, null, createContentValuesFromListItem(new DoItListItem(itemName), listId));
	}

	private ContentValues createContentValuesFromListItem(DoItListItem listItem, long listId)
	{
		ContentValues values = new ContentValues();
		values.put(SchemeListItem.COLUMN_NAME_ID, listItem.getId());
		values.put(SchemeListItem.COLUMN_NAME_TEXT, listItem.getText());
		values.put(SchemeListItem.COLUMN_LIST_ID, listId);
		return values;
	}

}
