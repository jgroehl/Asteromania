package de.asteromania.doitlist.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.asteromania.doitlist.domain.DoItTask;

public interface TasksDao
{

	final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

	List<DoItTask> getTasks(Date date);

	void deleteTasks(List<DoItTask> tasks);

	void updateTask(DoItTask task);

	void storeTask(DoItTask task);

	DoItTask getTask(long selectedTaskId);

}