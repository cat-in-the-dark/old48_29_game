package com.catinthedark;

import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.catinthedark.entities.Bullet;
import com.catinthedark.entities.Rocket;
import com.catinthedark.level.Level;

public class InterceptionManager {

	private final Level level;

	public InterceptionManager(Level level) {
		this.level = level;
	}

	/**
	 * Ракеты Джорджа деактивируют пули террористов
	 */
	private void deactivateTerroristBullets() {
		@SuppressWarnings("unchecked")
		List<Rocket> rockets = (List<Rocket>) (Object) level.levelEntities
				.get(Rocket.class);
		@SuppressWarnings("unchecked")
		List<Bullet> bullets = (List<Bullet>) (Object) level.levelEntities
				.get(Bullet.class);

		for (Rocket rocket : rockets) {
			for (Bullet bullet : bullets) {
				if (rocket.isMarkedToDelete() || bullet.isMarkedToDelete())
					continue;
				if (Intersector.intersectRectangles(rocket.bounds,
						bullet.bounds, new Rectangle())) {
					System.out.print("intercept!");
					rocket.markDeleted();
					bullet.markDeleted();
				}
			}
		}
	}

	public void manage() {
		deactivateTerroristBullets();
	}
}
