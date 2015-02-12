package de.asteromania.doitlist.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import de.asteromania.doitlist.R;
import de.asteromania.doitlist.domain.DoItTask;

public class DoItCreateActivity extends AbstractDoItActivity
{

	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_doittask);

		editText = (EditText) findViewById(R.id.edittext_create_doittask);
	}

	public void okayClicked(View view)
	{
		if (!editText.getText().toString().trim().isEmpty())
			getDoItTasks().add(new DoItTask(editText.getText().toString()));
		finish();
	}

	public void cancelClicked(View view)
	{
		finish();
	}

}
