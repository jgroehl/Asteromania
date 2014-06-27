package de.jgroehl.asteromania.control;

import java.util.ArrayList;
import java.util.List;

import de.jgroehl.asteromania.graphics.game.Enemy;

public class LevelHandler {

	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	private enum LevelType {
		NORMAL, BOSS, ASTEROIDS, MIXED;
	}

	public boolean isLevelOver() {
		for (Enemy e : enemies) {
			if (e.isAlive())
				return false;
		}
		return true;
	}

	public List<Enemy> getLevelObjects(int level) {

		enemies.clear();

		switch (getRandomLevelType()) {
		case ASTEROIDS:
			break;
		case BOSS:
			break;
		case MIXED:
			break;
		case NORMAL:
			break;
		default:
			break;
		}

		return enemies;
	}

	public LevelType getRandomLevelType() {
		return LevelType.values()[(int) (Math.random() * LevelType.values().length)];
	}

}
