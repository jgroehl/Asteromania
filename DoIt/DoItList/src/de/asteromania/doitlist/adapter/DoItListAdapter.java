package de.asteromania.doitlist.adapter;

import java.util.Collection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.activities.AbstractDoItActivity;
import de.asteromania.doitlist.domain.DoItList;
import de.asteromania.doitlist.intent.IntentHandler;
import de.asteromania.doitlist.intent.IntentHandler.Intent;

public class DoItListAdapter extends AbstractAdapter<DoItList>
{

	public DoItListAdapter(Collection<? extends DoItList> lists, AbstractDoItActivity context)
	{
		super(lists, context);
	}

	@Override
	@SuppressLint("ViewHolder")
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.rowlayout_list, parent, false);
		final TextView textView = (TextView) rowView.findViewById(R.id.row_list_textview);
		textView.setText(getItem(position).getName());
		
		textView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				getContext().getDataDao().setSelectedListId(getItem(position).getId());
				IntentHandler.startIntent(Intent.SHOW_LIST, v.getContext());
			}
		});
		
		return rowView;
	}

}
