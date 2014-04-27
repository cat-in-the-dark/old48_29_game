package com.catinthedark.level;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.catinthedark.Constants;
import com.catinthedark.GameScore;
import com.catinthedark.entities.*;
import com.catinthedark.screens.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Level {
    public President president;
    public final GameScreen gameScreen;
    public Map<Class, List<Entity>> levelEntities = new HashMap<Class, List<Entity>>();
    private final Rectangle tmpRect = new Rectangle();
    private Class[] renderOrder;

    public Level(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        president = new President(0, Constants.GROUND_LEVEL);
        renderOrder = new Class[] {
                House.class,
                Rocket.class,
                Bullet.class,
                OilFactory.class,
                OilField.class,
                TntVehicle.class,
                AidVehicle.class,
                Explosion.class
        };

        for (Class cls : renderOrder) {
            levelEntities.put(cls, new ArrayList<Entity>());
        }
    }

    public boolean isRighterThanViewPort(Entity entity) {
        Camera camera = gameScreen.getCamera();
        return (entity.getX() - camera.position.x) > camera.viewportWidth / 2f;
    }

    private boolean isInViewPort(Entity entity) {
        Camera camera = gameScreen.getCamera();
        return (camera.position.x - (entity.getX() + entity.getWidth())) < camera.viewportWidth / 1.5f;
    }

    private boolean isBulletInViewPort(Entity entity) {
        Camera camera = gameScreen.getCamera();
        return ((camera.position.x - entity.getX()) < camera.viewportWidth / 2f) && ((entity.getX() - camera.position.x) < camera.viewportWidth * 1.1f && entity.getY() < camera.viewportHeight && entity.getY() > Constants.GROUND_LEVEL);
    }

    public void render(float delta, SpriteBatch batch) {
        LevelGenerator.getInstance().generateLevel(this);

        for(Class cls : renderOrder){
            if(cls == Rocket.class) {
                for (Entity rocket : levelEntities.get(cls)) {
                    if (!isBulletInViewPort(rocket)) {
                        rocket.markDeleted();
                        bum(rocket.getX(), rocket.getY() - 0.2f);
                    } else {
                        rocket.render(delta, batch);
                    }
                }
            } else if (cls == OilField.class){
                renderEntities(levelEntities.get(cls), delta, batch);
                president.render(delta, batch);
            } else {
                renderEntities(levelEntities.get(cls), delta, batch);
            }
        }

        for (Class entityClass: levelEntities.keySet()) {
            Entity temp = clearEntities(entityClass);
            while (temp != null) {
                levelEntities.get(entityClass).remove(temp);
                temp = clearEntities(entityClass);
            }
        }
    }

    private void renderEntities(List<Entity> entities, float delta, SpriteBatch batch) {
        for (Entity entity : entities) {
            if (!isInViewPort(entity)) {
                entity.markDeleted();
            } else {
                entity.render(delta, batch);
            }
        }
    }

    private Entity clearEntities(Class entityClass) {
        for (Entity entity : levelEntities.get(entityClass)) {
            if (entity.isMarkedToDelete()) {
                return entity;
            }
        }
        return null;
    }

    public void shut(President president) {
        Rocket rocket = president.shut();
        if (rocket != null) {
            levelEntities.get(Rocket.class).add(rocket);
        }
    }

    public void placeOilFactory() {
        Rectangle factoryRect = new Rectangle(president.getX()
                + President.WIDTH, president.getY() - 4, 1, 1);

        for (Entity ent : levelEntities.get(OilField.class)) {
            final OilField field = (OilField) ent;

            if (field.isHasFactory())
                continue;

            if (Intersector.intersectRectangles(field.bounds, factoryRect,
                    tmpRect)) {
                levelEntities.get(OilFactory.class).add(
                        new OilFactory(president.getX() + President.WIDTH));
                field.setHasFactory(true);
                GameScore.getInstance().incDemocracyLevel();

                break;
            }
        }
    }

    public void bum(float x, float y) {
        levelEntities.get(Explosion.class).add(new Explosion(x, y));
    }
}
