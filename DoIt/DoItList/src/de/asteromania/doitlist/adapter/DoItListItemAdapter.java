package de.asteromania.doitlist.adapter;

import java.util.Collection;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.activities.AbstractDoItActivity;
import de.asteromania.doitlist.domain.DoItListItem;

public class DoItListItemAdapter extends AbstractAdapter<DoItListItem>
{

	public DoItListItemAdapter(Collection<? extends DoItListItem> values, AbstractDoItActivity context)
	{
		super(values, context);
	}

	@Override
	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.rowlayout_list, parent, false);
		final TextView textView = (TextView) rowView.findViewById(R.id.row_list_textview);
		textView.setText(getItem(position).getText());
		return rowView;
	}

}
