package com.catinthedark.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.entities.President;
import com.catinthedark.screens.GameScreen;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Level {
    public President president;
    public final GameScreen gameScreen;

    public Level(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        president = new President(0, 0);
    }

    public void render(float delta, SpriteBatch batch) {
        president.render(delta, batch);
    }
}
