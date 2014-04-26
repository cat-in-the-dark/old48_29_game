package com.catinthedark.level;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.Constants;
import com.catinthedark.entities.Bullet;
import com.catinthedark.entities.Entity;
import com.catinthedark.entities.House;
import com.catinthedark.entities.President;
import com.catinthedark.entities.Rocket;
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


    public Level(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        president = new President(0, Constants.GROUND_LEVEL);
        levelEntities.put(House.class, new ArrayList<Entity>());
        levelEntities.put(Rocket.class, new ArrayList<Entity>());
        levelEntities.put(Bullet.class, new ArrayList<Entity>());
        
      //тут просто создадим пулю маджахета для тестинга
    	levelEntities.get(Bullet.class).add(new Bullet(16,10, 1, 1));
    }

    private boolean isInViewPort(Entity entity) {
        Camera camera = gameScreen.getCamera();
        return (camera.position.x - entity.getX()) < camera.viewportWidth / 2f;
    }

    private boolean isBulletInViewPort(Entity entity) {
        Camera camera = gameScreen.getCamera();
        return ((camera.position.x - entity.getX()) < camera.viewportWidth / 2f) && ((entity.getX() - camera.position.x) < camera.viewportWidth * 2f);
    }

    public void render(float delta, SpriteBatch batch) {
        president.render(delta, batch);

        LevelGenerator.getInstance().generateLevel(this);
        for(Map.Entry<Class, List<Entity>> entry : levelEntities.entrySet()){
            Class cls = entry.getKey();
            if(cls == Rocket.class) {
                for (Entity rocket : entry.getValue()) {
                    if (!isBulletInViewPort(rocket)) {
                        rocket.markDeleted();
                    } else {
                        rocket.render(delta, batch);
                    }
                }
            } else {
                for (Entity entity : entry.getValue()) {
                    if (!isInViewPort(entity)) {
                        entity.markDeleted();
                    } else {
                        entity.render(delta, batch);
                    }
                }
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
}
