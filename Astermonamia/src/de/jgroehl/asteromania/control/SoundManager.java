package de.jgroehl.asteromania.control;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {

	private final SoundPool soundPool = new SoundPool(5,
			AudioManager.STREAM_MUSIC, 100);
	private static final int NUMBER_OF_SOUNDS = 5;
	private static final double NUMBER_OF_HIT_SOUNDS = 3;
	private static final int CLICK_ID = 0;
	private static final int SHOT_ID = 1;
	private static final int COIN_ID = 2;
	private static final int HIT1_ID = 3;
	private static final int HIT2_ID = 4;
	private final int[] sounds;

	public SoundManager(Context context) {
		this.sounds = new int[NUMBER_OF_SOUNDS];

		sounds[CLICK_ID] = soundPool.load(context,
				de.jgroehl.asteromania.R.raw.click, 100);
		sounds[SHOT_ID] = soundPool.load(context,
				de.jgroehl.asteromania.R.raw.shoot, 100);
		sounds[COIN_ID] = soundPool.load(context,
				de.jgroehl.asteromania.R.raw.coin, 100);
		sounds[HIT1_ID] = soundPool.load(context,
				de.jgroehl.asteromania.R.raw.hit, 100);
		sounds[HIT2_ID] = soundPool.load(context,
				de.jgroehl.asteromania.R.raw.hit3, 100);
	}

	public void playClickSound() {
		playSound(CLICK_ID);
	}

	private void playSound(int soundNumber) {
		playSound(soundNumber, 1.0f);
	}

	private void playSound(int soundNumber, float rate, float volume) {
		if (soundNumber < 0 || soundNumber > sounds.length - 1)
			throw new IllegalArgumentException("No such sound in library.");

		soundPool.play(sounds[soundNumber], volume, volume, 1, 0, rate);
	}

	private void playSound(int soundNumber, float rate) {
		playSound(soundNumber, rate, 1.0f);
	}

	public void playNormalShotSound() {
		playSound(SHOT_ID, 1.6f);
	}

	public void playEnemyShotSound() {
		playSound(SHOT_ID, 0.5f);
	}

	public void playCoinSound() {
		playSound(COIN_ID);
	}

	public void playerPlayerHitSound() {
		playHitSound(0.8f);
	}

	public void playEnemyHitSound() {
		playHitSound(1.2f);
	}

	private void playHitSound(float rate) {
		int value = (int) (Math.random() * NUMBER_OF_HIT_SOUNDS);
		switch (value) {
		case 1:
		default:
			playSound(HIT1_ID, rate, 0.1f);
			break;
		case 2:
			playSound(HIT2_ID, rate, 0.1f);
			break;
		}
	}

}
