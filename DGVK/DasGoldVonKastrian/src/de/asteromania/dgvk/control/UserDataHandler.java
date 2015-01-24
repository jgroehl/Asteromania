package de.asteromania.dgvk.control;

import android.content.Context;
import de.asteromania.dgvk.dto.authentication.UserDto;
import de.jgroehl.api.crypto.CryptoHandler;
import de.jgroehl.api.io.FileHandler;

public class UserDataHandler
{

	public static final String SEPARATION_CHAR = "&";
	private static final String FILE_USER = "user";
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
		return createUserFromString(fileHandler.getDecryptedStringFromFile(FILE_USER));
	}

	public void logoutUser()
	{
		fileHandler.writeAndEncryptString("", FILE_USER);
	}

	private String createStringFromUser(UserDto user)
	{
		return user.getUsername() + SEPARATION_CHAR + user.getPassword() + SEPARATION_CHAR + user.getRole();
	}

	private UserDto createUserFromString(String userString)
	{
		String[] split = userString.split(SEPARATION_CHAR);
		if (split.length < 3)
			return null;
		return new UserDto(split[0], split[1], split[2]);
	}

}
