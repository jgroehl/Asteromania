package de.jgroehl.api.user;

import java.util.ArrayList;
import java.util.List;

import android.util.Base64;
import de.jgroehl.api.io.FileHandler;

/**
 * 
 * @author Janek Gröhl
 *
 */
public class FriendInviteHandler
{

	private final FileHandler fileHandler;
	private final String holderAppId;
	private final List<String> acceptedFriendCodes = new ArrayList<String>();

	/**
	 * 
	 * @param fileHandler
	 *            not <code>null</code>
	 * @param holderAppId
	 *            not <code>null</code> and not empty
	 */
	public FriendInviteHandler(FileHandler fileHandler, String holderAppId)
	{
		if (fileHandler == null)
			throw new NullPointerException("fileHandler");
		if (holderAppId == null || holderAppId.trim().isEmpty())
			throw new NullPointerException("holderAppId");

		this.fileHandler = fileHandler;
		this.holderAppId = holderAppId;

		loadAcceptedFriendCodes();
	}

	private void saveAcceptedFriendCodes()
	{
		// TODO Auto-generated method stub

	}

	private void loadAcceptedFriendCodes()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param friendAppId
	 *            not <code>null</code>
	 * @return <code>null</code>, if the given Id was the same as the phone
	 *         holder's id.
	 */
	public String getFriendCode(String friendAppId)
	{
		if (friendAppId == null)
			throw new NullPointerException(friendAppId);

		if (holderAppId.equals(friendAppId))
			return null;

		byte[] bytes = (holderAppId + friendAppId).getBytes();
		for (int i = 0; i < bytes.length; i++)
		{
			bytes[i] = (byte) (bytes[i] * 2 % Byte.MAX_VALUE);
		}
		return new String(Base64.encode(bytes, Base64.URL_SAFE));
	}

	/**
	 * 
	 * @param friendAppId
	 *            not <code>null</code> or empty
	 * @param code
	 *            not <code>null</code> or empty
	 * @return <code>true</code> if the given code is a valid code for this
	 *         device
	 */
	public boolean isValidFriendCode(String friendAppId, String code)
	{
		if (friendAppId == null)
			throw new NullPointerException("friendAppId");
		if (code == null)
			throw new NullPointerException("code");
		if (friendAppId.trim().isEmpty() || code.trim().isEmpty())
			throw new IllegalArgumentException("friendAppId or code was empty.");

		if (code.equals(getFriendCode(friendAppId)) && !acceptedFriendCodes.contains(code))
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
