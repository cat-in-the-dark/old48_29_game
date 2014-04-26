package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Entity {
    protected float y;
    protected float x;
    protected int width;
    protected int height;
    public final Rectangle bounds = new Rectangle();

    public Entity(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        bounds.setRect(x, y, width, height);
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public void render(float delta, SpriteBatch batch) {

    }
}
