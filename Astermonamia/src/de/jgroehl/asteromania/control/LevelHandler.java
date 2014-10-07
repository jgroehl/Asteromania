package de.jgroehl.asteromania.control;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.jgroehl.api.graphics.GameObject;
import de.jgroehl.api.graphics.interfaces.Killable;
import de.jgroehl.asteromania.graphics.Asteroid;
import de.jgroehl.asteromania.graphics.enemies.BossEnemy;
import de.jgroehl.asteromania.graphics.enemies.NormalEnemy;

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
			numberOfEnemies = random(level / 10, level / 10);
			if (numberOfEnemies > 5)
				numberOfEnemies = 5;
			else if (numberOfEnemies < 1)
				numberOfEnemies = 1;
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(BossEnemy.createBossEnemy(context, level));
			}
			break;
		case MIXED:
			numberOfEnemies = random((level + 1.0) / 1.5 * 0.9,
					(level + 1.0) / 1.5 * 1.1);
			if (numberOfEnemies < 1)
				numberOfEnemies = 1;
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Asteroid.createAsteroid(context, level));
			}
			numberOfEnemies = random(Math.cbrt(level) * 0.9,
					Math.cbrt(level) * 1.1);
			if (numberOfEnemies > 8)
				numberOfEnemies = 8;
			else if (numberOfEnemies < 1)
				numberOfEnemies = 1;
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(NormalEnemy.createNormalEnemy(context, level));
			}
			break;
		case NORMAL:
		default:
			numberOfEnemies = random(Math.sqrt(level) * 0.9,
					Math.sqrt(level) * 1.1);
			if (numberOfEnemies < 1)
				numberOfEnemies = 1;
			if (numberOfEnemies > 15)
				numberOfEnemies = 15;
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(NormalEnemy.createNormalEnemy(context, level));
			}
			break;
		}

		return enemies;
	}

	private int random(double lower, double upper) {
		return (int) Math.round(lower + Math.random() * (upper - lower));
	}

	public LevelType getLevelType(int level) {
		if (level < 3)
			return LevelType.NORMAL;
		else {
			switch (level % 10) {
			case 0:
				// TODO: Include Super boss mode!
				return LevelType.BOSS;
			case 5:
				return LevelType.BOSS;
			default:
				double random = Math.random();
				if (random < 0.2)
					return LevelType.MIXED;
				else if (random < 0.6)
					return LevelType.ASTEROIDS;
				else
					return LevelType.NORMAL;
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
