package de.asteromania.doitlist.activities;

import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.domain.DoItTask;

public class DoItUpdateActivity extends AbstractDoItActivity
{

	private EditText editText;
	private DatePicker datePicker;
	private DoItTask doItTask;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_doittask);

		editText = (EditText) findViewById(R.id.edittext_update_doittask);
		datePicker = (DatePicker) findViewById(R.id.date_picker_update_doittask);

		doItTask = getDatabase().getTask(getDataDao().getSelectedTaskId());

		if (doItTask == null)
			finish();
		else
		{
			editText.setText(doItTask.getText());
			Calendar c = Calendar.getInstance();
			c.setTime(doItTask.getDate());
			datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		}
	}

	public void okayClicked(View view)
	{
		if (!editText.getText().toString().trim().isEmpty())
		{
			doItTask.setDate(getDateFromDatePicker(datePicker));
			doItTask.setText(editText.getText().toString());
			getDatabase().updateTask(doItTask);
		}
		finish();
	}

}
