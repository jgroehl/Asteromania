package de.asteromania.doitlist.domain;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.simpleframework.xml.Default;

@Default
public class DoItTasks
{

	private TreeSet<DoItTask> tasks = new TreeSet<DoItTask>(new Comparator<DoItTask>()
	{
		@Override
		public int compare(DoItTask lhs, DoItTask rhs)
		{
			return lhs.getDate().compareTo(rhs.getDate());
		}
	});

	@Deprecated
	DoItTasks()
	{
	}

	public DoItTasks(Collection<? extends DoItTask> tasks)
	{
		if (tasks != null)
			this.tasks.addAll(tasks);
	}

	public Set<DoItTask> getTasks()
	{
		return tasks;
	}

	public void add(DoItTask doItTask)
	{
		tasks.add(doItTask);
	}

}
