package de.jgroehl.asteromania.control;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.jgroehl.api.graphics.GameObject;
import de.jgroehl.api.graphics.interfaces.Killable;
import de.jgroehl.asteromania.graphics.Asteroid;
import de.jgroehl.asteromania.graphics.Enemy;

public class LevelHandler {

	private ArrayList<Killable> enemies = new ArrayList<Killable>();

	private enum LevelType {
		NORMAL, BOSS, ASTEROIDS, MIXED;
	}

	public boolean isLevelOver() {
		for (Killable e : enemies) {
			if (e.isAlive())
				return false;
		}
		return true;
	}

	public List<Killable> getLevelObjects(Context context, int level) {

		enemies.clear();
		int numberOfEnemies;
		switch (getLevelType(level)) {
		case ASTEROIDS:
			numberOfEnemies = random(level * 0.9, level * 0.1);
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Asteroid.createAsteroid(context, level));
			}
			break;
		case BOSS:
			numberOfEnemies = random(Math.cbrt(level) * 0.9,
					Math.cbrt(level) * 1.1);
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Enemy.createBossEnemy(context, level));
			}
			break;
		case MIXED:
			numberOfEnemies = random((level + 1.0) / 2.0 * 0.9,
					(level + 1.0) / 2.0 * 1.1);
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Asteroid.createAsteroid(context, level));
			}
			numberOfEnemies = random(Math.cbrt(level) * 0.9,
					Math.cbrt(level) * 1.1);
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Enemy.createNormalEnemy(context, level));
			}
			numberOfEnemies = random(Math.pow(level, 0.2) * 0.9,
					Math.pow(level, 0.2) * 1.1);
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Enemy.createBossEnemy(context, level));
			}
			break;
		case NORMAL:
		default:
			numberOfEnemies = random(Math.sqrt(level) * 0.9,
					Math.sqrt(level) * 1.1);
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Enemy.createNormalEnemy(context, level));
			}
			break;
		}

		return enemies;
	}

	private int random(double lower, double upper) {
		return (int) Math.round(lower + Math.random() * (upper - lower));
	}

	public LevelType getLevelType(int level) {
		if (level < 5)
			return LevelType.NORMAL;
		else {
			switch (level % 5) {
			case 0:
				return LevelType.BOSS;
			case 1:
				return LevelType.NORMAL;
			default:
				if (Math.random() <= 0.5)
					return LevelType.MIXED;
				else
					return LevelType.ASTEROIDS;
			}
		}
	}

	public void killAllEntities(AsteromaniaGameHandler gameHandler) {
		for (Killable k : enemies) {
			k.kill();
			if (k instanceof GameObject)
				gameHandler.remove((GameObject) k);
		}

	}

}
