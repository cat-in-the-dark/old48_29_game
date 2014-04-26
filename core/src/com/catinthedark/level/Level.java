package com.catinthedark.level;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.entities.Entity;
import com.catinthedark.entities.House;
import com.catinthedark.entities.President;
import com.catinthedark.entities.Rocket;
import com.catinthedark.screens.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Level {
    public President president;
    public final GameScreen gameScreen;
    public Map<Class, ArrayList<Entity>> levelEntities = new HashMap<Class, ArrayList<Entity>>();

    public Level(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        president = new President(0, 0);
        levelEntities.put(House.class, new ArrayList<Entity>());
        levelEntities.put(Rocket.class, new ArrayList<Entity>());
    }

    private boolean isInViewPort(Entity entity) {
        Camera camera = gameScreen.getCamera();
        return (Math.abs(camera.position.x - entity.getX() + entity.getWidth()) <= camera.viewportWidth / 2f);
    }

    public void render(float delta, SpriteBatch batch) {
        president.render(delta, batch);

        LevelGenerator.getInstance().generateLevel(this);

        for (Class entityClass: levelEntities.keySet()) {
            for (Entity entity: levelEntities.get(entityClass)) {
                if (!isInViewPort(entity)) {
                    levelEntities.get(entityClass).remove(entity);
                } else {
                    entity.render(delta, batch);
                }
            }
        }
    }

    public void shut(President president) {
        Rocket rocket = president.shut();
        if (rocket != null) {
            levelEntities.get(Rocket.class).add(rocket);
        }
    }
}
