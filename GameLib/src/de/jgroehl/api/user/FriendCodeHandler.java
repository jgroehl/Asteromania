package de.jgroehl.api.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.util.Base64;
import de.jgroehl.api.io.FileHandler;

/**
 * 
 * @author Janek Gröhl
 *
 */
public class FriendCodeHandler
{

	private final FileHandler fileHandler;
	private final List<String> acceptedFriendCodes = new ArrayList<String>();
	private static final String SPLIT_CHARACTER = "&";
	private static final String FILE_NAME_FRIEND_CODES = "friendCodes";

	/**
	 * 
	 * @param fileHandler
	 *            not <code>null</code>
	 * @param holderAppId
	 *            not <code>null</code>
	 */
	public FriendCodeHandler(FileHandler fileHandler)
	{
		if (fileHandler == null)
			throw new NullPointerException("fileHandler");

		this.fileHandler = fileHandler;

		loadAcceptedFriendCodes();
	}

	private void saveAcceptedFriendCodes()
	{
		StringBuilder sb = new StringBuilder();
		for (String s : acceptedFriendCodes)
		{
			sb.append(s + SPLIT_CHARACTER);
		}

		fileHandler.writeAndEncryptString(FILE_NAME_FRIEND_CODES, sb.toString());

	}

	private void loadAcceptedFriendCodes()
	{
		String s = fileHandler.getDecryptedStringFromFile(FILE_NAME_FRIEND_CODES);
		String[] codes = s.split(SPLIT_CHARACTER);

		for (String code : codes)
			acceptedFriendCodes.add(code);

	}

	/**
	 * 
	 * @param friendAppId
	 *            not <code>null</code>
	 * @return <code>null</code>, if the given Id was the same as the phone
	 *         holder's id.
	 */
	public String getFriendCode(String friendAppId, String holderAppId)
	{
		if (friendAppId == null)
			throw new NullPointerException(friendAppId);

		if (holderAppId.equals(friendAppId))
			return null;

		byte[] bytes = (holderAppId + friendAppId).getBytes();
		for (int i = 0; i < bytes.length / 2; i++)
		{
			bytes[i] = (byte) ((bytes[i + 1] + bytes[i * 2 + 1]) % Byte.MAX_VALUE);
		}
		return new String(Base64.encode(bytes, Base64.URL_SAFE)).toLowerCase(Locale.GERMAN).substring(2, 6);
	}

	/**
	 * 
	 * @param friendAppId
	 *            not <code>null</code>
	 * @param code
	 *            not <code>null</code>
	 * @return <code>true</code> if the given code is a valid code for this
	 *         device
	 */
	public boolean isValidFriendCode(String friendAppId, String holderAppId, String code)
	{
		if (friendAppId == null)
			throw new NullPointerException("friendAppId");
		if (code == null)
			throw new NullPointerException("code");

		if (code.equals(getFriendCode(friendAppId, holderAppId)) && !acceptedFriendCodes.contains(code))
		{
			acceptedFriendCodes.add(code);
			saveAcceptedFriendCodes();
			return true;
		}
		else
		{
			return false;
		}
	}

}
