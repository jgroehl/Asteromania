package de.asteromania.doitlist.domain;

public class DoItListItem implements DbObject
{
	private final long id;
	private String text;
	private boolean selected;

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

	@Override
	public long getId()
	{
		return id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
}
