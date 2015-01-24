package de.asteromania.dgvk.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class HashUtils
{
	private HashUtils()
	{

	}

	public static String hashString(String stringToHash, int repititions) throws NoSuchAlgorithmException
	{
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
		String result = stringToHash;
		for (int i = 0; i < repititions; i++)
		{
			messageDigest.update(result.getBytes());
			result = new String(Base64.getEncoder().encode(messageDigest.digest()));
		}
		return result;
	}
}
