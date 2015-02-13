package de.asteromania.doitlist.activities;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.domain.DoItTask;

public class DoItCreateActivity extends AbstractDoItActivity
{

	private EditText editText;
	private DatePicker datePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_doittask);

		editText = (EditText) findViewById(R.id.edittext_create_doittask);
		datePicker = (DatePicker) findViewById(R.id.date_picker_create_doittask);
	}

	public void okayClicked(View view)
	{
		if (!editText.getText().toString().trim().isEmpty())
			getDoItTasks().add(new DoItTask(editText.getText().toString(), getDateFromDatePicket(datePicker)));
		finish();
	}

	public Date getDateFromDatePicket(DatePicker datePicker)
	{
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year = datePicker.getYear();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 23, 59, 59);

		return calendar.getTime();
	}

}
