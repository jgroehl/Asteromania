package de.asteromania.doitlist.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.activities.AbstractDoItActivity;
import de.asteromania.doitlist.domain.DoItListItem;
import de.asteromania.doitlist.intent.IntentHandler;
import de.asteromania.doitlist.intent.IntentHandler.Intent;

public class DoItListItemAdapter extends AbstractAdapter<DoItListItem>
{

	public DoItListItemAdapter(Collection<? extends DoItListItem> values, AbstractDoItActivity context)
	{
		super(values, context);
	}

	@Override
	@SuppressLint("ViewHolder")
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.rowlayout_list_items, parent, false);
		final TextView textView = (TextView) rowView.findViewById(R.id.row_list_item_textview);
		textView.setText(getItem(position).getText());
		textView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.d(DoItListItemAdapter.class.getSimpleName(), "ListItem was clicked on.");
				getContext().getDataDao().setSelectedListItem(getItem(position).getId());
				IntentHandler.startIntent(Intent.UPDATE_LIST_ITEM, v.getContext());
			}
		});

		final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.row_list_item_checkbox);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				getItem(position).setSelected(isChecked);
			}
		});

		return rowView;
	}

	public Collection<DoItListItem> getSelectedItems()
	{
		List<DoItListItem> items = new ArrayList<DoItListItem>();
		for (DoItListItem i : getValues())
			if (i.isSelected())
				items.add(i);
		return items;
	}

}
