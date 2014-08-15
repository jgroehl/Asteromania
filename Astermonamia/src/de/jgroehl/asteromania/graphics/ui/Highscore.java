package de.jgroehl.asteromania.graphics.ui;

import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.graphics.Canvas;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GameObject;

public class Highscore extends GameObject {

	private class HighscoreElement implements Comparable<HighscoreElement> {

		public HighscoreElement(String date, Long score) {
			this.date = date;
			this.score = score;
		}

		public final String date;
		public final Long score;

		@Override
		public int compareTo(HighscoreElement otherHighscoreElement) {
			return score.compareTo(otherHighscoreElement.score);
		}
	}

	private Set<HighscoreElement> highscores = new TreeSet<HighscoreElement>();

	public Highscore(Context context) {
		super(0, 0, context);
		readHighscoreElements();
	}

	private void readHighscoreElements() {

	}

	@Override
	public void update(GameHandler gameHandler) {
	}

	@Override
	public void draw(Canvas c) {

	}

}
