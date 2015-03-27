package de.asteromania.doitlist.test;

import java.util.List;
import java.util.UUID;

import android.test.AndroidTestCase;
import de.asteromania.doitlist.dao.DatabaseHelper;
import de.asteromania.doitlist.dao.ListDao;
import de.asteromania.doitlist.dao.ListDaoImpl;
import de.asteromania.doitlist.domain.DoItList;

public class DbTests extends AndroidTestCase
{

	ListDao listDao;

	@Override
	protected void setUp() throws Exception
	{
		DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
		listDao = new ListDaoImpl(databaseHelper);
		super.setUp();
	}

	public void testInsertOneListAndRead()
	{
		String listName = UUID.randomUUID().toString();
		listDao.createList(listName);

		List<DoItList> lists = listDao.getEmptyLists();

		boolean found = false;
		for (DoItList list : lists)
		{
			if (listName.equals(list.getName()))
				found = true;
		}
		assertTrue("List was not successfully stored", found);
	}

	public void testInsertMultipleListsAndRead()
	{
		String[] listNames = new String[] { UUID.randomUUID().toString(), UUID.randomUUID().toString(),
				UUID.randomUUID().toString() };
		for (String listName : listNames)
		{
			listDao.createList(listName);
		}

		List<DoItList> lists = listDao.getEmptyLists();

		for (String listName : listNames)
		{
			boolean found = false;
			for (DoItList list : lists)
			{
				if (listName.equals(list.getName()))
					found = true;
			}
			assertTrue("List was not successfully stored", found);
		}
	}

	public void testGetListName()
	{
		String listName = UUID.randomUUID().toString();
		listDao.createList(listName);

		List<DoItList> lists = listDao.getEmptyLists();

		DoItList list = null;

		for (DoItList l : lists)
			if (listName.equals(l.getName()))
				list = l;

		assertNotNull(list);

		long id = list.getId();

		String retrievedListName = listDao.getListName(id);

		assertNotNull("Retrieved list name was null", retrievedListName);
		assertEquals("Retrieved list name was not correct", listName, retrievedListName);

	}

	@Override
	protected void tearDown() throws Exception
	{
		List<DoItList> lists = listDao.getEmptyLists();
		for (DoItList list : lists)
			listDao.deleteList(list.getId());
		super.tearDown();
	}

}
