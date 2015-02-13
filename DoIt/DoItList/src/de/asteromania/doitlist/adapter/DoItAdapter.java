package de.asteromania.doitlist.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.domain.DoItTask;

@SuppressLint("ViewHolder")
public class DoItAdapter extends BaseAdapter
{

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
	private final Context context;
	private final ArrayList<DoItTask> values = new ArrayList<DoItTask>();

	public DoItAdapter(Collection<? extends DoItTask> values, Context context)
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
	public DoItTask getItem(int position)
	{
		return values.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return values.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		final TextView textView = (TextView) rowView.findViewById(R.id.row_textview);
		textView.setText(dateFormat.format(getItem(position).getDate()) + ": " + getItem(position).getText());
		formatTextView(position, textView);

		final CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.row_checkbox);
		checkbox.setChecked(getItem(position).isFinished());
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				getItem(position).setFinished(isChecked);
				formatTextView(position, textView);
				textView.invalidate();
			}
		});

		return rowView;
	}

	private void formatTextView(final int position, final TextView textView)
	{
		if (getItem(position).isFinished())
		{
			textView.setTextColor(Color.GREEN);
			textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		else if (getItem(position).getDate().compareTo(Calendar.getInstance().getTime()) <= 0)
		{
			textView.setTextColor(Color.RED);
			textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
		else
		{
			textView.setTextColor(Color.BLACK);
			textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
	}

}
