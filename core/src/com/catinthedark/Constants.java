package com.catinthedark;

import com.badlogic.gdx.math.Vector2;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com Date: 26.04.14 Time: 13:36
 */
public class Constants {
	public static final int HEALTH_MAX = 100;
	public static final int DEMOCRACY_LEVEL_MAX = 10;
	public static final float GROUND_LEVEL = 4.0f; // tiles
	/**
	 * minimum distance between buildings (at max democracy level)
	 */
	public static final float BUILDING_DISTANCE_MIN = 5.0f; // tiles
	public static final Vector2 backCameraSpeed = new Vector2(0.2f, 0f);
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    public static final int ENEMY_PRICE = 200;
    public static final int TNT_VEHICLE_PRICE = 500;
    public static final int HOUSE_PRISE = 50;
    public static final int AID_VEHICLE_PRICE = 1000;

    public static float maxPresidentDestinationFromBorder = 4f;
	public static final Vector2 mainCameraSpeed = new Vector2(0.5f, 0f);
    public static final int SHOOT_INTERVAL_MIN = 2;  // sec
    public static final int SHOOT_INTERVAL_MAX = 6;  // sec
    public static final long SHOT_ANIMATION_DURATION = 500000000L;  //nanosec
    public static final float BULLET_SPEED_X = 0.2f;
    public static final float OIL_FIELD_DISTANCE_MIN = 15f;
    public static final float OIL_FIELD_DISTANCE_MAX = 32f;
    public static final float OIL_FIELD_Y_POS = 0.5f;

    public static final int TNT_VEHICLE_INTERVAL_MIN = 3;  // sec
    public static final int TNT_VEHICLE_INTERVAL_MAX = 5;  // sec
    public static final float TNT_VEHICLE_SPEED_X = 0.1f;

    public static final int AID_VEHICLE_INTERVAL_MIN = 8;  // sec
    public static final int AID_VEHICLE_INTERVAL_MAX = 12;  // sec
    public static final float AID_VEHICLE_SPEED_X = 0.5f;

	/**
	 * viewport
	 */
	public static final int VIEW_PORT_WIDTH = 32;
	public static final int VIEW_PORT_HEIGHT = 20;
	public static final int UNIT_SIZE = 32;

	public static final int FRAME_COLS = 4;
	public static final int FRAME_ROWS = 7;
	public static final float ANIMATION_SPEED = 0.1f;
    public static final float ANIMATION_SPEED_SLOW = 0.2f;
	
	public static final int TERR_DAMAGE = 4;
	
	public static final int HEALTH_REGEN = 25;
}
