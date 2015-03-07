package de.asteromania.doitlist.domain;

import java.util.Date;

public class DoItTask implements DbObject
{
	private final long id;
	private String text;
	private Date date;
	private boolean finished;

	public DoItTask(String text, Date date)
	{
		id = System.currentTimeMillis();
		this.text = text;
		this.date = date;
		finished = false;
	}

	public DoItTask(long id, String text, Date date, boolean finished)
	{
		this.id = id;
		this.text = text;
		this.date = date;
		this.finished = finished;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public boolean isFinished()
	{
		return finished;
	}

	@Override
	public long getId()
	{
		return id;
	}

	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
}
