package com.catinthedark.level;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.catinthedark.entities.Entity;
import com.catinthedark.entities.President;
import com.catinthedark.screens.GameScreen;

import java.util.ArrayList;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Level {
    public President president;
    public final GameScreen gameScreen;
    public ArrayList<Entity> levelEntities = new ArrayList<Entity>();

    public Level(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        president = new President(0, 0);
    }

    private boolean isInViewPort(Entity entity) {
        Camera camera = gameScreen.getCamera();
        return (Math.abs(camera.position.x - entity.getX() + entity.getWidth()) <= camera.viewportWidth / 2f);
    }

    public void render(float delta, SpriteBatch batch) {
        president.render(delta, batch);
        LevelGenerator.getInstance().generateLevel(this);
        for (Entity entity : levelEntities) {
            if (!isInViewPort(entity)) {
                levelEntities.remove(entity);
            } else {
                entity.render(delta, batch);
            }
        }
    }
}
