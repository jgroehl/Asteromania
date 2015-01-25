package de.asteromania.dgvk.activities;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import de.asteromania.dgvk.R;
import de.asteromania.dgvk.dto.authentication.UserDto;
import de.asteromania.dgvk.net.DgvkUrlProperties;
import de.jgroehl.api.net.AbstractHttpTask.OnResponseCallback;
import de.jgroehl.api.net.HttpPostTask;

public class RegisterActivity extends Activity
{

	private static final String TAG = RegisterActivity.class.getSimpleName();
	private EditText username;
	private EditText password;
	private EditText passwordRep;
	private TextView errorText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		username = (EditText) findViewById(R.id.edtRegUsername);
		password = (EditText) findViewById(R.id.edtRegPassword);
		passwordRep = (EditText) findViewById(R.id.edtRegPasswordRep);
		errorText = (TextView) findViewById(R.id.lblRegError);
	}

	public void register(View view)
	{
		String user = username.getText().toString();
		String pw = password.getText().toString();
		String pwRep = passwordRep.getText().toString();

		if (inputValid(user, pw, pwRep))
		{
			if (pw.equals(pwRep))
			{
				try
				{
					new HttpPostTask(DgvkUrlProperties.userUrl(), new UserDto(user, pw).toXml(), user,
							new OnResponseCallback()
							{

								@Override
								public void onSuccess(String result)
								{
									finish();
								}

								@Override
								public void onError(int resultCode)
								{
									switch (resultCode)
									{
										case HttpStatus.SC_BAD_REQUEST:
											errorText.setVisibility(TextView.VISIBLE);
											errorText.setText(getString(R.string.username_not_available));
											break;
										default:
											showInternalError();
											break;
									}

								}
							}).execute();
				}
				catch (Exception e)
				{
					Log.e(TAG, "Error: ", e);
					showInternalError();
				}
			}
			else
			{
				errorText.setVisibility(TextView.VISIBLE);
				errorText.setText(getString(R.string.identical_passwords));
			}
		}
		else
		{
			errorText.setVisibility(TextView.VISIBLE);
			errorText.setText(getString(R.string.invalid_credentials));
		}
	}

	private boolean inputValid(String user, String pw, String pwRep)
	{
		return (user != null && !user.isEmpty() && pw != null && !pw.isEmpty() && pwRep != null && !pwRep.isEmpty());
	}

	private void showInternalError()
	{
		errorText.setVisibility(TextView.VISIBLE);
		errorText.setText(getString(R.string.internal_error));
	}
}
