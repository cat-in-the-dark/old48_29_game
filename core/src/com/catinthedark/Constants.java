package com.catinthedark;

import com.badlogic.gdx.math.Vector2;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com
 * Date: 26.04.14
 * Time: 13:36
 */
public class Constants {
    public static final int HEALTH_MAX = 100;
    public static final int DEMOCRACY_LEVEL_MAX = 10;
    public static final float GROUND_LEVEL = 4.0f;  // tiles
    /**
     * minimum distance between buildings (at max democracy level)
     */
    public static final float BUILDING_DISTANCE_MIN = 5.0f;  // tiles
    public static final Vector2 backCameraSpeed = new Vector2(0.2f, 0f);
    public static final float maxPresidentDestinationFromBorder = 4f;
    public static final Vector2 mainCameraSpeed = new Vector2(0.5f, 0f);
}
