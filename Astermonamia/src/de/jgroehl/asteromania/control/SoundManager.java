package de.jgroehl.asteromania.control;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {

	private final SoundPool soundPool = new SoundPool(5,
			AudioManager.STREAM_MUSIC, 100);
	private static final int NUMBER_OF_SOUNDS = 2;
	private static final int CLICK_ID = 0;
	private static final int SHOT_ID = 1;
	private final int[] sounds;

	public SoundManager(Context context) {
		this.sounds = new int[NUMBER_OF_SOUNDS];

		sounds[CLICK_ID] = soundPool.load(context,
				de.jgroehl.asteromania.R.raw.click, 100);
		sounds[SHOT_ID] = soundPool.load(context,
				de.jgroehl.asteromania.R.raw.shoot, 100);
	}

	public void playClickSound() {
		playSound(CLICK_ID);
	}

	private void playSound(int soundNumber) {
		playSound(soundNumber, 1.0f);
	}

	private void playSound(int soundNumber, float rate) {
		if (soundNumber < 0 || soundNumber > sounds.length - 1)
			throw new IllegalArgumentException("No such sound in library.");

		soundPool.play(sounds[soundNumber], 1.0f, 1.0f, 1, 0, rate);
	}

	public void playNormalShotSound() {
		playSound(SHOT_ID, 1.2f);
	}

}
