package de.asteromania.doitlist.utils;

import java.util.Date;

public interface DataDao
{

	Date getSelectedDate();

	void setSelectedDate(Date date);

	long getSelectedTaskId();

	void setSelectedTaskId(long id);
}
