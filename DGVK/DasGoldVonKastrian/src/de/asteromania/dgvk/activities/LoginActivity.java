package de.asteromania.dgvk.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import de.asteromania.dgvk.R;
import de.asteromania.dgvk.control.UserDataHandler;
import de.asteromania.dgvk.dto.authentication.UserDto;
import de.jgroehl.api.crypto.CryptoHandler;

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
			userDataHandler.loginUser(new UserDto(user, pw));
			finish();
		}
		else
		{
			errorText.setVisibility(TextView.VISIBLE);
			errorText.setText(getString(R.string.invalid_credentials));
		}
	}

	private boolean inputValid(String user, String pw)
	{
		return (user != null && pw != null && !user.isEmpty() && !pw.isEmpty()
				&& !user.contains(UserDataHandler.SEPARATION_CHAR) && !pw.contains(UserDataHandler.SEPARATION_CHAR));
	}
}
