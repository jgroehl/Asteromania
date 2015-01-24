package de.asteromania.dgvk.test;

import java.security.NoSuchAlgorithmException;

import de.asteromania.dgvk.utils.HashUtils;

public class HashGenerator
{

	public static void main(String[] args) throws NoSuchAlgorithmException
	{
		System.out.println(HashUtils.hashString(
				"passwordjanekfb74d31d-cbf7-43e9-8053-66a14395ad17", 1013));
	}

}
