package de.asteromania.dgvk.control;

import android.content.Context;
import android.util.Log;
import de.asteromania.dgvk.dto.authentication.UserDto;
import de.jgroehl.api.crypto.CryptoHandler;
import de.jgroehl.api.io.FileHandler;

public class UserDataHandler
{

	public static final String SEPARATION_CHAR = "&";
	private static final String FILE_USER = "user";
	private static final String TAG = null;
	private final FileHandler fileHandler;

	public UserDataHandler(CryptoHandler cryptoHandler, Context context)
	{
		fileHandler = new FileHandler(cryptoHandler, context);
	}

	public void loginUser(UserDto user)
	{
		fileHandler.writeAndEncryptString(FILE_USER, createStringFromUser(user));
	}

	public UserDto getLoggedInUser()
	{
		Log.d(TAG, "Trying to get User");
		Log.d(TAG, "Parsing user from " + fileHandler.getDecryptedStringFromFile(FILE_USER));
		return createUserFromString(fileHandler.getDecryptedStringFromFile(FILE_USER));
	}

	public void logoutUser()
	{
		Log.d(TAG, "Logging out user..");
		fileHandler.writeAndEncryptString(FILE_USER, "");
	}

	private String createStringFromUser(UserDto user)
	{
		return user.getUsername() + SEPARATION_CHAR + user.getPassword() + SEPARATION_CHAR + user.getRole();
	}

	private UserDto createUserFromString(String userString)
	{
		String[] split = userString.split(SEPARATION_CHAR);
		if (split.length < 3)
		{
			Log.d(TAG, "No user logged in. Returning null");
			return null;
		}
		Log.d(TAG, "Found logged in user.");
		return new UserDto(split[0], split[1], split[2]);
	}

}
