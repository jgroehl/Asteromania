package de.asteromania.doitlist.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.simpleframework.xml.Default;

@Default
public class DoItTasks
{

	private ArrayList<DoItTask> tasks = new ArrayList<DoItTask>();

	@Deprecated
	DoItTasks()
	{
	}

	public DoItTasks(Collection<? extends DoItTask> tasks)
	{
		if (tasks != null)
			this.tasks.addAll(tasks);
	}

	public ArrayList<DoItTask> getTasks()
	{
		return tasks;
	}

	public void add(DoItTask doItTask)
	{
		tasks.add(doItTask);
	}

}
