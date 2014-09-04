package de.jgroehl.asteromania.player;

import android.content.Context;
import android.util.Log;
import de.jgroehl.asteromania.MainActivity;
import de.jgroehl.asteromania.graphics.game.statusBars.HpBar;
import de.jgroehl.asteromania.io.FileHandler;

public class PlayerInfo {

	private static final String TAG = PlayerInfo.class.getSimpleName();

	private static final String COIN_FILE_NAME = "coins";
	private static final String HP_FILE_NAME = "health";
	private static final String LEVEL_FILE_NAME = "level";
	private static final String STATS_FILE_NAME = "stats";
	private static final String HIGHSCORE_FILE_NAME = "currentHighscore";
	private static final String SCORE_FACTOR_FILE_NAME = "currentScoreFactor";
	private static final String SPLIT_CHARACTER = "&";

	private static final int DEFAULT_HEALTH = 3;
	private static final int DEFAULT_LEVEL = 1;
	private static final float DEFAULT_STAT_FACTOR = 1.0f;
	private static final int DEFAULT_BONUS_DAMAGE = 0;
	private static final long DEFAULT_HIGHSCORE = 0;
	private static final long DEFAULT_SCORE_FACTOR = 1;

	private static final float HEALTH_HEIGHT = 0.075f;
	private static final float HEALTH_WIDTH = 0.3f;
	private static final float HEALTH_X = 0.025f;
	private static final float HEALTH_Y = 0.90f;

	private static final int DEFAULT_COIN_VALUE = MainActivity.DEBUG ? 1000000
			: 100;

	private final Context context;
	private final FileHandler fileHandler;

	private HpBar healthPoints;
	private long coins;
	private int level;
	private float maxSpeedFactor;
	private float shotSpeedFactor;
	private float shotFrequencyFactor;
	private int bonusDamage;
	private long currentHighscore;
	private long currentScoreFactor;
	private long lastHighscore;

	public PlayerInfo(Context context, FileHandler fileHandler) {
		this.context = context;
		this.fileHandler = fileHandler;
		setUpPlayerInfo();
	}

	private void setUpPlayerInfo() {
		Log.d(TAG, "Loading playerInfo from internal memory...");
		readCoins();
		readHealth();
		readLevel();
		readStats();
		readCurrentHighscore();
		readCurrentScoreFactor();
		Log.d(TAG, "Loading playerInfo from internal memory...[Done]");
	}

	private void readCurrentScoreFactor() {
		try {
			currentScoreFactor = Long.parseLong(fileHandler
					.getDecryptedStringFromFile(SCORE_FACTOR_FILE_NAME));
			Log.d(TAG, "Set current Score Factor to " + currentScoreFactor);

		} catch (NumberFormatException e) {
			Log.w(TAG, "currentScoreFactor not readable. Setting level to "
					+ DEFAULT_SCORE_FACTOR);
			currentScoreFactor = DEFAULT_SCORE_FACTOR;
		}
	}

	private void readCurrentHighscore() {
		try {
			currentHighscore = Long.parseLong(fileHandler
					.getDecryptedStringFromFile(HIGHSCORE_FILE_NAME));
			Log.d(TAG, "Set current Highscore to " + currentHighscore);

		} catch (NumberFormatException e) {
			Log.w(TAG, "currentHighscore not readable. Setting level to "
					+ DEFAULT_HIGHSCORE);
			currentHighscore = DEFAULT_HIGHSCORE;
		}

	}

	private void readStats() {

		String[] stats = fileHandler
				.getDecryptedStringFromFile(STATS_FILE_NAME).split(
						SPLIT_CHARACTER);

		try {
			if (stats.length >= 1) {
				maxSpeedFactor = Float.parseFloat(stats[0]);
			} else {
				Log.w(TAG,
						"MaxSpeedFactor not readable. Reverting to default factor: "
								+ DEFAULT_STAT_FACTOR);
				maxSpeedFactor = DEFAULT_STAT_FACTOR;
			}
		} catch (NumberFormatException e) {
			Log.w(TAG,
					"MaxSpeedFactor not readable. Reverting to default factor: "
							+ DEFAULT_STAT_FACTOR);
			maxSpeedFactor = DEFAULT_STAT_FACTOR;
		}

		try {
			if (stats.length >= 2) {
				shotSpeedFactor = Float.parseFloat(stats[1]);
			} else {
				Log.w(TAG,
						"shotSpeedFactor not readable. Reverting to default factor: "
								+ DEFAULT_STAT_FACTOR);
				shotSpeedFactor = DEFAULT_STAT_FACTOR;
			}
		} catch (NumberFormatException e) {
			Log.w(TAG,
					"shotSpeedFactor not readable. Reverting to default factor: "
							+ DEFAULT_STAT_FACTOR);
			shotSpeedFactor = DEFAULT_STAT_FACTOR;
		}

		try {
			if (stats.length >= 3) {
				shotFrequencyFactor = Float.parseFloat(stats[2]);
			} else {
				Log.w(TAG,
						"shotFrequencyFactor not readable. Reverting to default factor: "
								+ DEFAULT_STAT_FACTOR);
				shotFrequencyFactor = DEFAULT_STAT_FACTOR;
			}
		} catch (NumberFormatException e) {
			Log.w(TAG,
					"shotFrequencyFactor not readable. Reverting to default factor: "
							+ DEFAULT_STAT_FACTOR);
			shotFrequencyFactor = DEFAULT_STAT_FACTOR;
		}

		try {
			if (stats.length >= 4) {
				bonusDamage = Integer.parseInt(stats[3]);
			} else {
				Log.w(TAG,
						"bonusDamage not readable. Reverting to default factor: "
								+ DEFAULT_BONUS_DAMAGE);
				bonusDamage = DEFAULT_BONUS_DAMAGE;
			}
		} catch (NumberFormatException e) {
			Log.w(TAG,
					"bonusDamage not readable. Reverting to default factor: "
							+ DEFAULT_BONUS_DAMAGE);
			bonusDamage = DEFAULT_BONUS_DAMAGE;
		}
	}

	protected void readLevel() {
		try {
			level = Integer.parseInt(fileHandler
					.getDecryptedStringFromFile(LEVEL_FILE_NAME));
			Log.d(TAG, "Set level to " + level);

		} catch (NumberFormatException e) {
			Log.w(TAG, "Current level not readable. Setting level to "
					+ DEFAULT_LEVEL);
			level = DEFAULT_LEVEL;
		}
	}

	protected void readHealth() {
		try {
			String[] health = fileHandler.getDecryptedStringFromFile(
					HP_FILE_NAME).split(SPLIT_CHARACTER);
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

	protected void readCoins() {
		try {
			coins = Integer.parseInt(fileHandler
					.getDecryptedStringFromFile(COIN_FILE_NAME));
			Log.d(TAG, "Set coins to " + coins);

		} catch (NumberFormatException e) {
			Log.w(TAG, "Amount of coins not readable. Setting coins to 0.");
			coins = DEFAULT_COIN_VALUE;
		}
	}

	public void savePlayerInfo() {
		Log.d(TAG, "Saving PlayerInfo...");
		fileHandler
				.writeAndEncryptString(COIN_FILE_NAME, String.valueOf(coins));
		fileHandler.writeAndEncryptString(
				HP_FILE_NAME,
				String.valueOf(healthPoints.getCurrentValue() + SPLIT_CHARACTER
						+ healthPoints.getMaximum()));
		fileHandler.writeAndEncryptString(LEVEL_FILE_NAME,
				String.valueOf(level));
		fileHandler.writeAndEncryptString(
				STATS_FILE_NAME,
				String.valueOf(maxSpeedFactor) + SPLIT_CHARACTER
						+ String.valueOf(shotSpeedFactor) + SPLIT_CHARACTER
						+ String.valueOf(shotFrequencyFactor) + SPLIT_CHARACTER
						+ String.valueOf(bonusDamage));
		fileHandler.writeAndEncryptString(HIGHSCORE_FILE_NAME,
				String.valueOf(currentHighscore));
		fileHandler.writeAndEncryptString(SCORE_FACTOR_FILE_NAME,
				String.valueOf(currentScoreFactor));
		Log.d(TAG, "Saving PlayerInfo...[Done]");

	}

	public void addCoins(int amount) {
		coins += amount;
	}

	public long getCoins() {
		return coins;
	}

	public HpBar getHealthPoints() {
		return healthPoints;
	}

	public void addHealthPoints(int value) {
		healthPoints.setMaximum(healthPoints.getMaximum() + value);
		healthPoints.setCurrentValue(healthPoints.getCurrentValue() + value);
	}

	public int getLevel() {
		return level;
	}

	public int nextLevel() {
		return ++level;
	}

	public float getMaxSpeedFactor() {
		return maxSpeedFactor;
	}

	public void addMaxSpeedFactor(float extraSpeed) {
		maxSpeedFactor += extraSpeed;
	}

	public float getShotSpeedFactor() {
		return shotSpeedFactor;
	}

	public float getShotFrequencyFactor() {
		return shotFrequencyFactor;
	}

	public int getBonusDamage() {
		return bonusDamage;
	}

	public void addBonusDamage(int increaseValue) {
		bonusDamage += increaseValue;
	}

	public void addShotFrequencyFactor(float increaseValue) {
		shotFrequencyFactor += increaseValue;

	}

	public void addShotSpeedFactor(float increaseValue) {
		shotSpeedFactor += increaseValue;
	}

	public void addScore(long value) {
		currentHighscore += value * currentScoreFactor;
		lastHighscore = currentHighscore;
	}

	public long getCurrentHighscore() {
		return currentHighscore;
	}

	public void reset() {
		healthPoints.reset();
		level = DEFAULT_LEVEL - 1;
		currentHighscore = DEFAULT_HIGHSCORE;
		currentScoreFactor = DEFAULT_SCORE_FACTOR;
	}

	public void resetLastHighscore() {
		lastHighscore = 0;
	}

	public void resetScoreBonus() {
		currentScoreFactor = DEFAULT_SCORE_FACTOR;
	}

	public String getBonusFactor() {
		return String.valueOf(currentScoreFactor);
	}

	public void incerementScoreFactor() {
		currentScoreFactor++;
	}

	public long getLastHighscore() {
		return lastHighscore;
	}
}
