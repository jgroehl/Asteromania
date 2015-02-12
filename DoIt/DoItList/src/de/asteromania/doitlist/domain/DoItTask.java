package de.asteromania.doitlist.domain;

import org.simpleframework.xml.Default;

@Default
public class DoItTask
{
	private long id = System.currentTimeMillis();
	private String text;
	private boolean finished = false;

	@Deprecated
	DoItTask()
	{
	}

	public DoItTask(String text)
	{
		this.text = text;
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
}
