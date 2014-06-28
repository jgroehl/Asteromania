package de.jgroehl.asteromania.control;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.jgroehl.asteromania.graphics.GameObject;
import de.jgroehl.asteromania.graphics.game.Asteroid;
import de.jgroehl.asteromania.graphics.game.Enemy;
import de.jgroehl.asteromania.graphics.interfaces.Killable;

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
		switch (getRandomLevelType()) {
		case ASTEROIDS:
			numberOfEnemies = random((level + 1.0) / 2.0, level);
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Asteroid.createAsteroid(context, level));
			}
			break;
		case BOSS:
			numberOfEnemies = random(1, Math.cbrt(level));
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Enemy.createBossEnemy(context, level));
			}
			break;
		case MIXED:
			numberOfEnemies = random((level + 3.0) / 4.0, (level + 1.0) / 2.0);
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Asteroid.createAsteroid(context, level));
			}
			numberOfEnemies = random(1, Math.cbrt(level));
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Enemy.createNormalEnemy(context, level));
			}
			numberOfEnemies = random(0, Math.sqrt(Math.sqrt(level)));
			for (int i = 0; i < numberOfEnemies; i++) {
				enemies.add(Enemy.createBossEnemy(context, level));
			}
			break;
		case NORMAL:
		default:
			numberOfEnemies = random(1, Math.sqrt(level));
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

	public LevelType getRandomLevelType() {
		return LevelType.values()[(int) (Math.random() * LevelType.values().length)];
	}

	public void killAllEntities(GameHandler gameHandler) {
		for (Killable k : enemies) {
			k.kill();
			if (k instanceof GameObject)
				gameHandler.remove((GameObject) k);
		}

	}

}
