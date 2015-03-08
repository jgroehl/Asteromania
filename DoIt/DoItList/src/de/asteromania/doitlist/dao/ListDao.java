package de.asteromania.doitlist.dao;

import java.util.List;

import de.asteromania.doitlist.domain.DoItList;
import de.asteromania.doitlist.domain.DoItListItem;

public interface ListDao
{
	void createList(String listName);

	List<DoItList> getEmptyLists();
	
	List<DoItListItem> getListItems(long listid);
	
	void deleteList(long listId);
}
