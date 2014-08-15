package de.jgroehl.asteromania.crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.util.Log;

public class CryptoHandler {

	private static final String TAG = CryptoHandler.class.getSimpleName();
	private SecretKeySpec secretKeySpec;
	private SecretKey sK;

	public class CryptoException extends Exception {
		private static final long serialVersionUID = 525936215367206677L;

		public CryptoException(Exception e) {
			super(e);
		}
	}

	public CryptoHandler(Context context) {
		FileInputStream fis;
		try {
			fis = context.openFileInput("cryptoKey");
			try {
				ObjectInputStream ois = new ObjectInputStream(fis);
				sK = (SecretKey) ois.readObject();
				ois.close();
				secretKeySpec = new SecretKeySpec(sK.getEncoded(), "AES");
			} catch (Exception e) {
				Log.e(TAG, "Fatal: Creating cryptoHandler failed.");
				System.exit(123);
			}
		} catch (FileNotFoundException e) {
			Log.w(TAG,
					"Couldn't find crypto key. Creating new one. Old data will be lost.");
			createCryptoKey(context);
		}
	}

	private void createCryptoKey(Context context) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = new SecureRandom();
			keyGenerator.init(secureRandom);
			sK = keyGenerator.generateKey();
			FileOutputStream fos = context.openFileOutput("cryptoKey",
					Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(sK);
			oos.close();
			secretKeySpec = new SecretKeySpec(sK.getEncoded(), "AES");
		} catch (Exception e) {
			Log.e(TAG,
					"Creating Crypto Key failed. Progress will not be saved.");
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
