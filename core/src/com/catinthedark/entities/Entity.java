package com.catinthedark.entities;

import java.awt.*;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Entity {
    private final int y;
    private final int x;
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
}
