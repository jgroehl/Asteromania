package de.asteromania.doitlist.domain;

public class DoItListItem
{
	private final long id;
	private String text;

	public DoItListItem(long id, String text)
	{
		this.id = id;
		this.text = text;
	}

	/**
	 * Creates a new {@link DoItListItem} with a generated unique id.
	 * 
	 * @param text
	 */
	public DoItListItem(String text)
	{
		this(System.currentTimeMillis(), text);
	}

	public long getId()
	{
		return id;
	}

	public String getText()
	{
		return text;
	}
}
