package de.jgroehl.asteromania.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import de.jgroehl.asteromania.crypto.CryptoHandler;
import de.jgroehl.asteromania.crypto.CryptoHandler.CryptoException;

public class PlayerInfo {

	private CryptoHandler cryptoHandler;
	private final Context context;

	private int coins;
	private final String COIN_FILE_NAME = "coins";

	public PlayerInfo(CryptoHandler cryptoHandler, Context context) {
		this.cryptoHandler = cryptoHandler;
		this.context = context;

		try {
			coins = Integer
					.parseInt(getDecryptedStringFromFile(COIN_FILE_NAME));
		} catch (NumberFormatException e) {
			Log.w(PlayerInfo.class.getSimpleName(),
					"Amount of coins not readable. This is because of missing file or invalid file contents.");
			coins = 0;
		}
	}

	public void savePlayerInfo() {
		Log.d(PlayerInfo.class.getSimpleName(), "Saving PlayerInfo...");
		writeAndEncryptString(COIN_FILE_NAME, String.valueOf(coins));
		Log.d(PlayerInfo.class.getSimpleName(), "Saving PlayerInfo...[Done]");

	}

	private void writeAndEncryptString(String fileName, String value) {
		try {
			FileOutputStream fos = context.openFileOutput(COIN_FILE_NAME,
					Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);

			bw.write(new String(cryptoHandler.encode(Base64.encode(
					value.getBytes(), Base64.DEFAULT))));

			bw.close();
			osw.close();
			fos.close();
		} catch (IOException e) {
			Log.e(PlayerInfo.class.getSimpleName(),
					"Saving value " + value + " for " + fileName
							+ " failed because of IO: " + e.getMessage());
		} catch (CryptoException e) {
			Log.e(PlayerInfo.class.getSimpleName(),
					"Saving value " + value + " for " + fileName
							+ " failed because of crypto: " + e.getMessage());
		}
	}

	private String getDecryptedStringFromFile(String fileName) {
		String result = "";
		try {
			FileInputStream coinInputStream = context.openFileInput(fileName);
			if (coinInputStream == null)
				return result;
			InputStreamReader isr = new InputStreamReader(coinInputStream);
			BufferedReader bufferedReader = new BufferedReader(isr);

			String readLine = bufferedReader.readLine();
			if (readLine == null)
				return result;
			result = new String(cryptoHandler.decode(Base64.decode(
					readLine.getBytes(), Base64.DEFAULT)));

			coinInputStream.close();
			isr.close();
			bufferedReader.close();
		} catch (IOException e) {
			Log.e(PlayerInfo.class.getSimpleName(),
					"Error while retrieving input string from file: "
							+ e.getMessage());
			return "";
		} catch (CryptoException e) {
			Log.e(PlayerInfo.class.getSimpleName(),
					"Crypto Exception while reading file: " + e.getMessage());
			return "";
		}

		return result;
	}

	public void addCoins(int amount) {
		coins += amount;
	}

	public int getCoins() {
		return coins;
	}

}
