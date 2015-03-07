package de.asteromania.doitlist.adapter;

import java.util.ArrayList;
import java.util.Collection;

import android.widget.BaseAdapter;
import de.asteromania.doitlist.activities.AbstractDoItActivity;
import de.asteromania.doitlist.domain.DbObject;

public abstract class AbstractAdapter<E extends DbObject> extends BaseAdapter
{

	private final AbstractDoItActivity context;
	private final ArrayList<E> values = new ArrayList<E>();

	public AbstractAdapter(Collection<? extends E> values, AbstractDoItActivity context)
	{
		this.context = context;
		if (values != null)
			this.values.addAll(values);
	}

	@Override
	public int getCount()
	{
		return values.size();
	}

	@Override
	public E getItem(int position)
	{
		return values.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return values.get(position).getId();
	}

	public AbstractDoItActivity getContext()
	{
		return context;
	}
}
