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
import de.jgroehl.asteromania.graphics.game.statusBars.HpBar;

public class PlayerInfo {

	private static final String TAG = PlayerInfo.class.getSimpleName();
	private static final String COIN_FILE_NAME = "coins";
	private static final String HP_FILE_NAME = "health";
	private static final String SPLIT_CHARACTER = "&";
	private static final int DEFAULT_HEALTH = 20;
	private static final float HEALTH_HEIGHT = 0.075f;
	private static final float HEALTH_WIDTH = 0.3f;
	private static final float HEALTH_X = 0.025f;
	private static final float HEALTH_Y = 0.90f;
	private final CryptoHandler cryptoHandler;
	private final Context context;

	private HpBar healthPoints;
	private int coins;

	public PlayerInfo(CryptoHandler cryptoHandler, Context context) {
		this.cryptoHandler = cryptoHandler;
		this.context = context;
		Log.d(TAG, "Loading playerInfo from internal memory...");
		setUpPlayerInfo();
		Log.d(TAG, "Loading playerInfo from internal memory...[Done]");
	}

	private void setUpPlayerInfo() {
		try {
			coins = Integer
					.parseInt(getDecryptedStringFromFile(COIN_FILE_NAME));
			Log.d(TAG, "Set coins to " + coins);

		} catch (NumberFormatException e) {
			Log.w(TAG, "Amount of coins not readable. Setting coins to 0.");
			coins = 0;
		}

		try {
			String[] health = getDecryptedStringFromFile(HP_FILE_NAME).split(
					SPLIT_CHARACTER);
			int currentValue = Integer.parseInt(health[0]);
			int maximum = Integer.parseInt(health[1]);

			healthPoints = new HpBar(maximum, currentValue, HEALTH_X, HEALTH_Y,
					HEALTH_WIDTH, HEALTH_HEIGHT, context);
			Log.d(TAG, "Set HP to: " + currentValue + "/" + maximum);
		} catch (NumberFormatException e) {
			Log.w(TAG,
					"Amount of health not readable. Setting to default value "
							+ DEFAULT_HEALTH);
			healthPoints = new HpBar(DEFAULT_HEALTH, DEFAULT_HEALTH, HEALTH_X,
					HEALTH_Y, HEALTH_WIDTH, HEALTH_HEIGHT, context);
		}
	}

	public void savePlayerInfo() {
		Log.d(TAG, "Saving PlayerInfo...");
		writeAndEncryptString(COIN_FILE_NAME, String.valueOf(coins));
		writeAndEncryptString(
				HP_FILE_NAME,
				String.valueOf(healthPoints.getCurrentValue() + SPLIT_CHARACTER
						+ healthPoints.getMaximum()));
		Log.d(TAG, "Saving PlayerInfo...[Done]");

	}

	private void writeAndEncryptString(String fileName, String value) {
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

	private String getDecryptedStringFromFile(String fileName) {
		String result = "";
		try {
			FileInputStream coinInputStream = context.openFileInput(fileName);
			if (coinInputStream == null) {
				Log.w(TAG, "When opening " + fileName
						+ " the file was not existing.");
				return result;
			}
			InputStreamReader isr = new InputStreamReader(coinInputStream);
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

			coinInputStream.close();
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

	public void addCoins(int amount) {
		coins += amount;
	}

	public int getCoins() {
		return coins;
	}

	public HpBar getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(HpBar healthPoints) {
		this.healthPoints = healthPoints;
	}

}
