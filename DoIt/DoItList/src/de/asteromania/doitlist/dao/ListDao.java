package de.asteromania.doitlist.dao;

import java.util.Collection;
import java.util.List;

import de.asteromania.doitlist.domain.DoItList;
import de.asteromania.doitlist.domain.DoItListItem;

public interface ListDao
{
	void createList(String listName);

	List<DoItList> getEmptyLists();

	List<DoItListItem> getListItems(long listid);

	void deleteList(long listId);

	String getListName(long selectedListId);

	DoItListItem getListItem(long selectedItemId);

	void updateListItem(DoItListItem doItListItem, long listId);

	void updateListName(long selectedListId, String string);

	void deleteItems(Collection<DoItListItem> selectedItems);

	void createListItem(String itemName, long listId);
}
