package de.asteromania.doitlist.domain;

public class DoItList implements DbObject
{
	private final long id;
	private String name;

	/**
	 * Creates a new {@link DoItList} with a generated unique id..
	 * 
	 * @param name
	 */
	public DoItList(String name)
	{
		this(System.currentTimeMillis(), name);
	}

	public DoItList(long id, String name)
	{
		this.id = id;
		this.name = name;
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
}
