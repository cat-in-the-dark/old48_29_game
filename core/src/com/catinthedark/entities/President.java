package com.catinthedark.entities;

/**
 * Created by Ilya on 26.04.2014.
 */
public class President extends Entity {
    private static final int presidentWidth = 3;
    private static final int presidentHeight = 10;

    public President(int x, int y) {
        super(x, y, presidentWidth, presidentHeight);
    }
}
