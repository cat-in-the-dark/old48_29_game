package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by Ilya on 26.04.2014.
 */
public class Entity {
    protected float y;
    protected float x;
    protected int width;
    protected int height;
    public final Rectangle bounds;
    protected Direction direction;
    protected boolean deleted;

    public enum Direction {
        RIGHT, LEFT
    }
    public enum State {
        IDLE, RUN, JUMP
    }

    public Entity(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        
        this.bounds =  new Rectangle(x, y, width, height);
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

    public void setDirection(Direction dir) {
        this.direction = dir;
    }

    public void render(float delta, SpriteBatch batch) {

    }

    public void markDeleted() {
        System.out.println("Kill me plz");
        this.deleted = true;
    }

    public boolean isMarkedToDelete() {
        return this.deleted;
    }
}
