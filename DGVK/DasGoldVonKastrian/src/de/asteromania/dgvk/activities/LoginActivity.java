package de.asteromania.dgvk.activities;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import de.asteromania.dgvk.R;
import de.asteromania.dgvk.control.UserDataHandler;
import de.asteromania.dgvk.dto.authentication.UserDto;
import de.asteromania.dgvk.net.DgvkUrlProperties;
import de.asteromania.dgvk.properties.IntentHandler;
import de.asteromania.dgvk.properties.IntentHandler.Intent;
import de.jgroehl.api.crypto.CryptoHandler;
import de.jgroehl.api.net.AbstractHttpTask.OnResponseCallback;
import de.jgroehl.api.net.HttpPostTask;

public class LoginActivity extends Activity
{
	private static final String TAG = LoginActivity.class.getSimpleName();
	private EditText username;
	private EditText password;
	private TextView errorText;
	private UserDataHandler userDataHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		username = (EditText) findViewById(R.id.edtUsername);
		password = (EditText) findViewById(R.id.edtPassword);
		errorText = (TextView) findViewById(R.id.lblLoginError);

		userDataHandler = new UserDataHandler(new CryptoHandler(this), this);
	}

	public void login(View view)
	{
		Log.d(TAG, "Login Method called");
		String user = username.getText().toString();
		String pw = password.getText().toString();

		if (inputValid(user, pw))
		{
			try
			{
				new HttpPostTask(DgvkUrlProperties.authenticationUrl(), new UserDto(user, pw).toXml(), user,
						new OnResponseCallback()
						{

							@Override
							public void onSuccess(String result)
							{
								try
								{
									userDataHandler.loginUser(new UserDto("", "").fromXml(result));
									finish();
								}
								catch (Exception e)
								{
									Log.e(TAG, "Error with xml: " + result);
									Log.e(TAG, "Error: ", e);
									showInternalError();
								}
							}

							@Override
							public void onError(int resultCode)
							{
								switch (resultCode)
								{
									case HttpStatus.SC_OK:
										break;
									case HttpStatus.SC_BAD_GATEWAY:
									case HttpStatus.SC_GATEWAY_TIMEOUT:
										errorText.setVisibility(TextView.VISIBLE);
										errorText.setText(getString(R.string.server_unreachable));
										break;
									case HttpStatus.SC_UNAUTHORIZED:
									case HttpStatus.SC_FORBIDDEN:
										errorText.setVisibility(TextView.VISIBLE);
										errorText.setText(getString(R.string.wrong_credentials));
										break;
									default:
										Log.d(TAG, "Showing internal Error message for SC_" + resultCode);
										showInternalError();
										break;
								}
							}
						}).execute();
			}
			catch (Exception e)
			{
				showInternalError();
			}
		}
		else
		{
			errorText.setVisibility(TextView.VISIBLE);
			errorText.setText(getString(R.string.invalid_credentials));
		}
	}

	public void switchToRegister(View view)
	{
		IntentHandler.startIntent(Intent.REGISTER, this);
	}

	private void showInternalError()
	{
		errorText.setVisibility(TextView.VISIBLE);
		errorText.setText(getString(R.string.internal_error));
	}

	private boolean inputValid(String user, String pw)
	{
		return (user != null && pw != null && !user.isEmpty() && !pw.isEmpty()
				&& !user.contains(UserDataHandler.SEPARATION_CHAR) && !pw.contains(UserDataHandler.SEPARATION_CHAR));
	}
}
