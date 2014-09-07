package de.jgroehl.asteromania.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import de.jgroehl.asteromania.crypto.CryptoHandler;
import de.jgroehl.asteromania.crypto.CryptoHandler.CryptoException;

public class SettingsHandler {

	private static final String NAME = "Settings";
	private static final String TAG_PLAY_SOUNDS = "playSounds";
	private static final String TAG = SettingsHandler.class.getSimpleName();
	private final SharedPreferences settings;
	private final CryptoHandler cryptoHandler;

	private boolean playSounds;

	public SettingsHandler(Context context, CryptoHandler cryptoHandler) {
		this.cryptoHandler = cryptoHandler;
		settings = context.getSharedPreferences(NAME, 0);

		try {
			playSounds = Boolean.getBoolean(new String(cryptoHandler
					.decode(settings
							.getString(
									TAG_PLAY_SOUNDS,
									new String(cryptoHandler.encode("false"
											.getBytes()))).getBytes())));
		} catch (CryptoException e) {
			Log.e(TAG, "Error loading settings: " + e.getMessage());
		}
	}

	private void setValue(String key, String value,
			SharedPreferences.Editor editor) {
		try {
			editor.putString(key,
					new String(cryptoHandler.encode(value.getBytes())));
		} catch (CryptoException e) {
			Log.e(TAG, "Error while wrotong setting: " + key);
		}
	}

	public void setPlaySounds(boolean playSounds) {
		this.playSounds = playSounds;
	}

	public boolean playSounds() {
		return playSounds;
	}

	public void store() {
		SharedPreferences.Editor editor = settings.edit();
		setValue(TAG_PLAY_SOUNDS, String.valueOf(playSounds), editor);
		editor.commit();
	}

}
