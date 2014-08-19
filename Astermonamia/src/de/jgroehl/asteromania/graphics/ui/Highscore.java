package de.jgroehl.asteromania.graphics.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.graphics.GameObject;
import de.jgroehl.asteromania.graphics.interfaces.Clickable;
import de.jgroehl.asteromania.io.FileHandler;

public class Highscore extends GameObject implements Clickable {

	private class HighscoreElement implements Comparable<HighscoreElement> {

		public HighscoreElement(String date, Long score) {
			this.date = date;
			this.score = score;
		}

		public final String date;
		public final Long score;

		@Override
		public int compareTo(HighscoreElement otherHighscoreElement) {
			return -score.compareTo(otherHighscoreElement.score);
		}
	}

	private static final String HIGHSCORE_FILE_NAME = "Highscores";
	private static final String CLASS_SPLIT_CHAR = "%";
	private static final String ELEMENT_SPLIT_CHAR = "&";
	private static final String TAG = Highscore.class.getSimpleName();

	private final Set<HighscoreElement> highscores = new TreeSet<HighscoreElement>();
	private final FileHandler fileHandler;
	private Paint tablePaint = new Paint();
	private Paint bigTextPaint;
	private Paint smallTextPaint;

	public Highscore(Context context, FileHandler fileHandler) {
		super(0, 0, context);
		this.fileHandler = fileHandler;
		readHighscoreElements();
		tablePaint.setColor(Color.WHITE);
		tablePaint.setStyle(Style.STROKE);
	}

	private void readHighscoreElements() {
		String highscoreValues = fileHandler
				.getDecryptedStringFromFile(HIGHSCORE_FILE_NAME);
		for (String classString : highscoreValues.split(CLASS_SPLIT_CHAR)) {
			try {
				highscores.add(new HighscoreElement(classString
						.split(ELEMENT_SPLIT_CHAR)[0], Long.valueOf(classString
						.split(ELEMENT_SPLIT_CHAR)[1])));
			} catch (NumberFormatException e) {
				// Do not add Element to list but ignore. The invalid Element
				// will be deleted next save.
				Log.w(TAG, "Format error on retrieved HighscoreElement");
			} catch (ArrayIndexOutOfBoundsException e) {
				// Same as above..
				Log.w(TAG, "Too little Data on retrieved HighscoreElement");
			}
		}
	}

	@Override
	public void update(GameHandler gameHandler) {
	}

	@Override
	public void draw(Canvas c) {

		if (bigTextPaint == null) {
			bigTextPaint = new Paint();
			bigTextPaint.setColor(Color.WHITE);
			bigTextPaint.setTextSize(c.getHeight() * 0.26f);
		}
		if (smallTextPaint == null) {
			smallTextPaint = new Paint();
			smallTextPaint.setColor(Color.WHITE);
			smallTextPaint.setTextSize(c.getHeight() * 0.08f);
		}

		c.drawRect(c.getWidth() * 0.1f, c.getHeight() * 0.1f,
				c.getWidth() * 0.9f, c.getHeight() * 0.9f, tablePaint);
		c.drawLine(c.getWidth() * 0.1f, c.getHeight() * 0.36f,
				c.getWidth() * 0.9f, c.getHeight() * 0.36f, tablePaint);
		c.drawLine(c.getWidth() * 0.1f, c.getHeight() * 0.62f,
				c.getWidth() * 0.9f, c.getHeight() * 0.62f, tablePaint);

		c.drawText("1.", c.getWidth() * 0.11f, c.getHeight() * 0.315f,
				bigTextPaint);
		c.drawText("2.", c.getWidth() * 0.11f, c.getHeight() * 0.575f,
				bigTextPaint);
		c.drawText("3.", c.getWidth() * 0.11f, c.getHeight() * 0.835f,
				bigTextPaint);

		Iterator<HighscoreElement> iterator = highscores.iterator();
		if (highscores.size() >= 1) {
			HighscoreElement highscoreElement = iterator.next();
			c.drawText(highscoreElement.date, c.getWidth() * 0.25f,
					c.getHeight() * 0.27f, smallTextPaint);
			c.drawText(String.valueOf(highscoreElement.score),
					c.getWidth() * 0.55f, c.getHeight() * 0.27f, smallTextPaint);
		}
		if (highscores.size() >= 2) {
			HighscoreElement highscoreElement = iterator.next();
			c.drawText(highscoreElement.date, c.getWidth() * 0.25f,
					c.getHeight() * 0.53f, smallTextPaint);
			c.drawText(String.valueOf(highscoreElement.score),
					c.getWidth() * 0.55f, c.getHeight() * 0.53f, smallTextPaint);
		}
		if (highscores.size() >= 3) {
			HighscoreElement highscoreElement = iterator.next();
			c.drawText(highscoreElement.date, c.getWidth() * 0.25f,
					c.getHeight() * 0.79f, smallTextPaint);
			c.drawText(String.valueOf(highscoreElement.score),
					c.getWidth() * 0.55f, c.getHeight() * 0.79f, smallTextPaint);
		}

	}

	public void addNewHighscore(long highscore) {
		highscores.add(new HighscoreElement(new SimpleDateFormat("dd.MM.yyyy",
				Locale.GERMANY).format(new Date()), highscore));
		saveHighscores();
	}

	private void saveHighscores() {
		if (highscores.size() > 0) {
			String highscoresString = "";
			if (highscores.size() == 1) {
				highscoresString = highscoresString
						+ highscores.toArray(new HighscoreElement[] {})[0].date
						+ ELEMENT_SPLIT_CHAR
						+ highscores.toArray(new HighscoreElement[] {})[0].score;
			} else if (highscores.size() == 2) {
				highscoresString = highscoresString
						+ highscores.toArray(new HighscoreElement[] {})[0].date
						+ ELEMENT_SPLIT_CHAR
						+ highscores.toArray(new HighscoreElement[] {})[0].score
						+ CLASS_SPLIT_CHAR
						+ highscores.toArray(new HighscoreElement[] {})[1].date
						+ ELEMENT_SPLIT_CHAR
						+ highscores.toArray(new HighscoreElement[] {})[1].score;
			} else {
				highscoresString = highscoresString
						+ highscores.toArray(new HighscoreElement[] {})[0].date
						+ ELEMENT_SPLIT_CHAR
						+ highscores.toArray(new HighscoreElement[] {})[0].score
						+ CLASS_SPLIT_CHAR
						+ highscores.toArray(new HighscoreElement[] {})[1].date
						+ ELEMENT_SPLIT_CHAR
						+ highscores.toArray(new HighscoreElement[] {})[1].score
						+ CLASS_SPLIT_CHAR
						+ highscores.toArray(new HighscoreElement[] {})[2].date
						+ ELEMENT_SPLIT_CHAR
						+ highscores.toArray(new HighscoreElement[] {})[2].score;
			}
			fileHandler.writeAndEncryptString(HIGHSCORE_FILE_NAME,
					highscoresString);
		}
	}

	public boolean isNewHighscore(long currentHighscore) {
		return highscores.toArray(new HighscoreElement[] {})[0].score <= currentHighscore;
	}

	@Override
	public boolean isClicked(int x, int y, int screenWidth, int screenHeight) {
		return true;
	}

	@Override
	public void performAction(GameHandler gameHandler) {
		gameHandler.setState(GameState.MAIN);
	}

}
