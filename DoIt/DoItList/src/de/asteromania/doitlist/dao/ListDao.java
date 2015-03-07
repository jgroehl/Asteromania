package de.asteromania.doitlist.dao;

import java.util.List;

import de.asteromania.doitlist.domain.DoItList;

public interface ListDao
{

	void createList(String listName);

	List<DoItList> getEmptyLists();

}
