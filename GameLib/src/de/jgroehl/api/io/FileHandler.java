package de.jgroehl.api.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import de.jgroehl.api.crypto.CryptoHandler;
import de.jgroehl.api.crypto.CryptoHandler.CryptoException;

public class FileHandler {

	private static final String TAG = FileHandler.class.getSimpleName();
	private final CryptoHandler cryptoHandler;
	private final Context context;

	public FileHandler(CryptoHandler cryptoHandler, Context context) {
		this.cryptoHandler = cryptoHandler;
		this.context = context;
	}

	public void writeAndEncryptString(String fileName, String value) {
		try {
			FileOutputStream fos = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);

			String output = new String(Base64.encode(
					cryptoHandler.encode(value.getBytes()), Base64.URL_SAFE));
			Log.d(TAG, "Saved value [" + output + "] to " + fileName);
			bw.write(output);

			bw.close();
			osw.close();
			fos.close();
		} catch (IOException e) {
			Log.e(TAG, "Saving value " + value + " for " + fileName
					+ " failed because of IO: " + e.getMessage());
		} catch (CryptoException e) {
			Log.e(TAG, "Saving value " + value + " for " + fileName
					+ " failed because of crypto: " + e.getMessage());
		}
	}

	public String getDecryptedStringFromFile(String fileName) {
		String result = "";
		try {
			FileInputStream fis = context.openFileInput(fileName);
			if (fis == null) {
				Log.w(TAG, "When opening " + fileName
						+ " the file was not existing.");
				return result;
			}
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(isr);

			String readLine = bufferedReader.readLine();
			if (readLine == null) {
				Log.w(TAG, "When reading " + fileName
						+ " the file was not readable or empty.");
				return result;
			}
			Log.d(TAG, "Read value [" + readLine + "] from " + fileName);
			result = new String(cryptoHandler.decode(Base64.decode(
					readLine.getBytes(), Base64.URL_SAFE)));

			fis.close();
			isr.close();
			bufferedReader.close();
		} catch (IOException e) {
			Log.e(TAG,
					"Error while retrieving input string from file: "
							+ e.getMessage());
			return "";
		} catch (CryptoException e) {
			Log.e(TAG, "Crypto Exception while reading file: " + e.getMessage());
			return "";
		}

		return result;
	}

	public String readFileContents(String fileName) {
		String result = null;
		FileInputStream fis;
		try {
			fis = context.openFileInput(fileName);
		} catch (FileNotFoundException e) {
			return result;
		}
		if (fis == null)
			return result;

		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader bufferedReader = new BufferedReader(isr);

		StringBuilder sb = new StringBuilder();

		String s;
		try {
			while ((s = bufferedReader.readLine()) != null) {
				sb.append(s);
			}
		} catch (IOException e) {
			Log.e(TAG, "Error reading contents from File " + fileName);
		}
		return sb.toString();
	}

}
