package de.asteromania.doitlist.adapter;

import java.util.Collection;

import android.view.View;
import android.view.ViewGroup;
import de.asteromania.doitlist.activities.AbstractDoItActivity;
import de.asteromania.doitlist.domain.DoItList;

public class DoItListAdapter extends AbstractAdapter<DoItList>
{

	public DoItListAdapter(Collection<? extends DoItList> lists, AbstractDoItActivity context)
	{
		super(lists, context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
