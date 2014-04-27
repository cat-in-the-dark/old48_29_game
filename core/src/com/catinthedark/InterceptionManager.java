package com.catinthedark;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.catinthedark.assets.Assets;
import com.catinthedark.entities.*;
import com.catinthedark.level.Level;

import java.util.List;

public class InterceptionManager {

	private final Level level;
	private static final Rectangle tmpRect = new Rectangle();

	public InterceptionManager(Level level) {
		this.level = level;
	}

	private void entityBothDestroy(List<Entity> list1, List<Entity> list2) {
		for (Entity entity1 : list1) {
			for (Entity entity2 : list2) {
				if (entity1.isMarkedToDelete() || entity2.isMarkedToDelete())
					continue;
				if (Intersector.intersectRectangles(entity1.bounds,
						entity2.bounds, tmpRect)) {
					Assets.smallExpSound.play();
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
							block.bounds, tmpRect)) {
						Assets.explosionSound.play();
						if (block.isWithEnemy()) {
							GameScore.getInstance().priceEnemy();
						}
						GameScore.getInstance().priceHouse();
						rocket.markDeleted();
						block.setDestroyed(true);
                        level.bum(block.getX(), block.getY());
					}
				}
			}
		}

	}

	private void damagePresident() {
		@SuppressWarnings("unchecked")
		List<Bullet> bullets = (List<Bullet>) (Object) level.levelEntities
				.get(Bullet.class);
		President pres = level.president;

		for (Bullet bullet : bullets) {
			if (bullet.isMarkedToDelete())
				continue;

			for (Rectangle bound : pres.getBounds()) {
				if (Intersector.intersectRectangles(bound, bullet.bounds,
						tmpRect)) {
					pres.doDamage(Constants.TERR_DAMAGE);
					bullet.markDeleted();
					break;
				}
			}
		}

	}

	private void destroyKamaz() {
		@SuppressWarnings("unchecked")
		List<Rocket> rockets = (List<Rocket>) (Object) level.levelEntities
				.get(Rocket.class);
		@SuppressWarnings("unchecked")
		List<TntVehicle> vehicles = (List<TntVehicle>) (Object) level.levelEntities
				.get(TntVehicle.class);

		for (Rocket rocket : rockets) {
			for (TntVehicle vehicle : vehicles) {
				if (rocket.isMarkedToDelete() || vehicle.isDestroyed())
					continue;
				if (Intersector.intersectRectangles(rocket.bounds,
						vehicle.bounds, tmpRect)) {
					rocket.markDeleted();
					vehicle.setDestroyed(true);
					Assets.kamazExpSound.play();
					GameScore.getInstance().priceTNTVehicle();
				}
			}
		}
	}

	private void destroyAid() {
		@SuppressWarnings("unchecked")
		List<Rocket> rockets = (List<Rocket>) (Object) level.levelEntities
				.get(Rocket.class);
		@SuppressWarnings("unchecked")
		List<AidVehicle> vehicles = (List<AidVehicle>) (Object) level.levelEntities
				.get(AidVehicle.class);

		for (Rocket rocket : rockets) {
			for (AidVehicle vehicle : vehicles) {
				if (rocket.isMarkedToDelete() || vehicle.isDestroyed())
					continue;
				if (Intersector.intersectRectangles(rocket.bounds,
						vehicle.bounds, tmpRect)) {
					rocket.markDeleted();
					vehicle.setDestroyed(true);
					Assets.kamazExpSound.play();
					GameScore.getInstance().priceAIdVehicle();
				}
			}
		}

	}

	private void kamazDestroysFactory() {
		@SuppressWarnings("unchecked")
		List<TntVehicle> vehicles = (List<TntVehicle>) (Object) level.levelEntities
				.get(TntVehicle.class);
		@SuppressWarnings("unchecked")
		List<OilFactory> factories = (List<OilFactory>) (Object) level.levelEntities
				.get(OilFactory.class);

		for (TntVehicle vehicle : vehicles) {
			if (vehicle.isDestroyed())
				continue;
			
			for (OilFactory factory : factories) {
				if (Intersector.intersectRectangles(vehicle.bounds,
						factory.bounds, tmpRect)) {
					vehicle.setDestroyed(true);
					factory.setDestroyed(true);

					GameScore.getInstance().decDemocracyLevel();
					
					Assets.kamazExpSound.play();
					
					break;
				}
			}
		}

	}

	public void manage() {
		deactivateTerroristBullets();
		destroyHouseBlock();
		damagePresident();
		destroyKamaz();
		destroyAid();
		kamazDestroysFactory();
	}
}
