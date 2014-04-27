package com.catinthedark;

import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.catinthedark.entities.Bullet;
import com.catinthedark.entities.Entity;
import com.catinthedark.entities.House;
import com.catinthedark.entities.HouseBlock;
import com.catinthedark.entities.Rocket;
import com.catinthedark.level.Level;

public class InterceptionManager {

	private final Level level;

	public InterceptionManager(Level level) {
		this.level = level;
	}

	private void entityBothDestroy(List<Entity> list1, List<Entity> list2) {
		for (Entity entity1 : list1) {
			for (Entity entity2 : list2) {
				if (entity1.isMarkedToDelete() || entity2.isMarkedToDelete())
					continue;
				if (Intersector.intersectRectangles(entity1.bounds,
						entity2.bounds, new Rectangle())) {
					System.out.print("intercept!");
					entity1.markDeleted();
					entity2.markDeleted();
				}
			}
		}
	}

	/**
	 * Ракеты Джорджа деактивируют пули террористов
	 */
	private void deactivateTerroristBullets() {
		entityBothDestroy(level.levelEntities.get(Rocket.class),
				level.levelEntities.get(Bullet.class));
	}

	private void destroyHouseBlock() {
		@SuppressWarnings("unchecked")
		List<Rocket> rockets = (List<Rocket>) (Object) level.levelEntities
				.get(Rocket.class);
		@SuppressWarnings("unchecked")
		List<House> houses = (List<House>) (Object) level.levelEntities
				.get(House.class);

		for (Rocket rocket : rockets) {
			for (House house : houses) {
				for (HouseBlock block : house.getHouseBlocks()) {
					if (rocket.isMarkedToDelete() || block.isDestroyed())
						continue;

					if (Intersector.intersectRectangles(rocket.bounds,
							block.bounds, new Rectangle())) {
						System.out.print("intercept block with bazooka!");
						System.out.print("block rect:" + block.bounds);
						rocket.markDeleted();
						block.setDestroyed(true);
					}
				}
			}
		}

	}

	public void manage() {
		deactivateTerroristBullets();
		destroyHouseBlock();
	}
}
