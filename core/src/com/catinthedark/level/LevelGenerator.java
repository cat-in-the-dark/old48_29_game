package com.catinthedark.level;

import com.badlogic.gdx.utils.TimeUtils;
import com.catinthedark.Constants;
import com.catinthedark.entities.House;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com
 * Date: 26.04.14
 * Time: 14:31
 */
public class LevelGenerator {
    private static LevelGenerator ourInstance = new LevelGenerator();
    private long baseTime;

    public static LevelGenerator getInstance() {
        return ourInstance;
    }

    private LevelGenerator() {
        baseTime = TimeUtils.nanoTime();
    }

    public void generateLevel(Level level) {

    }
}
