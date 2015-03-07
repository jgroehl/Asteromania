package de.asteromania.doitlist.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DoItList implements DbObject
{

	private final long id;
	private String name;
	private final List<DoItListItem> listItems = new ArrayList<DoItListItem>();

	/**
	 * Creates a new {@link DoItList} with a generated unique id and and empty
	 * list of {@link DoItListItem} Objects.
	 * 
	 * @param name
	 */
	public DoItList(String name)
	{
		this(System.currentTimeMillis(), name, null);
	}

	public DoItList(long id, String name, Collection<? extends DoItListItem> listItems)
	{
		super();
		this.id = id;
		this.name = name;
		if (listItems != null)
			this.listItems.addAll(listItems);
	}

	@Override
	public long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public List<DoItListItem> getListItems()
	{
		return listItems;
	}

}
