package de.asteromania.doitlist.domain;

import java.util.Date;

import org.simpleframework.xml.Default;

@Default
public class DoItTask
{
	private long id = System.currentTimeMillis();
	private String text;
	private Date date;
	private boolean finished = false;

	@Deprecated
	DoItTask()
	{
	}

	public DoItTask(String text, Date date)
	{
		this.text = text;
		this.date = date;
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
}
