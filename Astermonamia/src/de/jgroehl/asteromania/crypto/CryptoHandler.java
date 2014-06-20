package de.jgroehl.asteromania.crypto;

import java.io.ObjectInputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.content.res.Resources;
import android.util.Log;

public class CryptoHandler {

	private SecretKeySpec secretKeySpec;
	private SecretKey sK;

	public class CryptoException extends Exception {
		private static final long serialVersionUID = 525936215367206677L;

		public CryptoException(Exception e) {
			super(e);
		}
	}

	public CryptoHandler(Resources resources) {
		try {
			ObjectInputStream ois = new ObjectInputStream(
					resources
							.openRawResource(de.jgroehl.asteromania.R.raw.megasound));
			sK = (SecretKey) ois.readObject();
			ois.close();
			secretKeySpec = new SecretKeySpec(sK.getEncoded(), "AES");
		} catch (Exception e) {
			Log.e(CryptoHandler.class.getSimpleName(),
					"Creating cryptoHandlöer failed.");
			System.exit(123);
		}
	}

	public byte[] encode(byte[] bytes) throws CryptoException {

		try {
			Cipher c = Cipher.getInstance("AES");
			c.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			return c.doFinal(bytes);
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

	public byte[] decode(byte[] bytes) throws CryptoException {
		try {
			Cipher c = Cipher.getInstance("AES");
			c.init(Cipher.DECRYPT_MODE, secretKeySpec);
			return c.doFinal(bytes);
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

}
