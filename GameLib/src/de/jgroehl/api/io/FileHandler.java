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

/**
 * The {@link FileHandler} can be used to write and read strings from files
 * which are private to the application as specified in {@link Context}
 * .MODE_PRIVATE.
 * 
 * The given strings will not be written directly but will be encrypted and
 * decrypted with a key only known to the phone holder. For these encryption the
 * {@link FileHandler} uses the {@link CryptoHandler} class.
 * 
 * @author Janek Gröhl
 *
 */
public class FileHandler {

	private static final String TAG = FileHandler.class.getSimpleName();
	private final CryptoHandler cryptoHandler;
	private final Context context;

	/**
	 * 
	 * @param cryptoHandler
	 *            not <code>null</code>
	 * @param context
	 *            not <code>null</code>
	 */
	public FileHandler(CryptoHandler cryptoHandler, Context context) {
		if (cryptoHandler == null)
			throw new NullPointerException(
					"cryptoHandler was null in FileHandler");
		if (context == null)
			throw new NullPointerException("Context was null in FileHandler");

		this.cryptoHandler = cryptoHandler;
		this.context = context;
	}

	/**
	 * 
	 * @param fileName
	 *            not <code>null</code>
	 * @param value
	 *            not <code>null</code>
	 */
	public void writeAndEncryptString(String fileName, String value) {

		if (fileName == null)
			throw new NullPointerException(
					"FileName was null when trying to write: " + value);
		if (value == null)
			throw new NullPointerException(
					"Value field was null when trying to write String.");

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

	/**
	 * 
	 * @param fileName
	 *            not <code>null</code>
	 * @return the decrypted content of the given file.
	 */
	public String getDecryptedStringFromFile(String fileName) {
		if (fileName == null)
			throw new NullPointerException(
					"File name was null when trying to read file contents.");
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

	/**
	 * 
	 * @param fileName
	 *            not <code>null</code>
	 * @return the entire content written to the specified file wrapped in a
	 *         single String.
	 */
	public String readFileContents(String fileName) {
		if (fileName == null)
			throw new NullPointerException(
					"fileName was null when reading file content.");
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
