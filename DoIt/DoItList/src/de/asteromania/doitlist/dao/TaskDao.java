package de.asteromania.doitlist.dao;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.asteromania.doitlist.domain.DoItListItem;
import de.asteromania.doitlist.domain.DoItTask;

public interface TaskDao
{

	final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

	List<DoItTask> getTasks(Date date);

	void deleteTasks(Collection<? extends DoItTask> tasks);

	void updateTask(DoItTask task);

	void storeTask(DoItTask task);

	DoItTask getTask(long selectedTaskId);

	void createTasks(Collection<? extends DoItListItem> values, Date selectedDate);

}