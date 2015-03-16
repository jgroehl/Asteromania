package de.asteromania.doitlist.adapter;

import java.util.Collection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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
import de.asteromania.doitlist.domain.DoItTask;
import de.asteromania.doitlist.intent.IntentHandler;
import de.asteromania.doitlist.intent.IntentHandler.Intent;

@SuppressLint("ViewHolder")
public class DoItTaskAdapter extends AbstractAdapter<DoItTask>
{

	public DoItTaskAdapter(Collection<? extends DoItTask> lists, AbstractDoItActivity context)
	{
		super(lists, context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		final TextView textView = (TextView) rowView.findViewById(R.id.row_textview);
		textView.setText(getItem(position).getText());
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
				getContext().getTaskDao().updateTask(getItem(position));
			}
		});

		textView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				getContext().getDataDao().setSelectedTaskId(getItem(position).getId());
				IntentHandler.startIntent(Intent.UPDATE, v.getContext());
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
		else
		{
			textView.setTextColor(Color.BLACK);
			textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
	}

}
