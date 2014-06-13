package de.jgroehl.asteromania.crypto;

import java.io.ObjectInputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.content.res.Resources;

public class CryptoHandler {

	private final SecretKeySpec secretKeySpec;
	private final Cipher c;
	private final SecretKey sK;

	public class CryptoException extends Exception {
		private static final long serialVersionUID = 525936215367206677L;

		public CryptoException(Exception e) {
			super(e);
		}
	}

	public CryptoHandler(Resources res) throws CryptoException {
		try {
			ObjectInputStream ois = new ObjectInputStream(
					res.openRawResource(de.jgroehl.asteromania.R.raw.megasound));
			sK = (SecretKey) ois.readObject();
			ois.close();
			secretKeySpec = new SecretKeySpec(sK.getEncoded(), "AES");

			c = Cipher.getInstance("AES");
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

	public byte[] encode(byte[] bytes) throws CryptoException {

		try {
			c.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			return c.doFinal(bytes);
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

	public byte[] decode(byte[] bytes) throws CryptoException {
		try {
			c.init(Cipher.DECRYPT_MODE, secretKeySpec);
			return c.doFinal(bytes);
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

}
