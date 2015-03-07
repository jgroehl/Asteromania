package de.asteromania.doitlist.dao;

import java.util.Date;

public interface DataDao
{

	Date getSelectedDate();

	void setSelectedDate(Date date);

	long getSelectedTaskId();

	void setSelectedTaskId(long id);

	void setSelectedListId(long id);

	long getSelectedListId();
}
