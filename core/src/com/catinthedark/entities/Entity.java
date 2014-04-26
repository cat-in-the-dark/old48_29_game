package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Entity {
    protected int y;
    protected int x;
    protected int width;
    protected int height;
    public final Rectangle bounds = new Rectangle();

    public Entity(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        bounds.setBounds(x, y, width, height);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public void render(float delta, SpriteBatch batch) {

    }
}
