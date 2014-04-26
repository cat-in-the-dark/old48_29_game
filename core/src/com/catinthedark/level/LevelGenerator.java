package com.catinthedark.level;

import com.badlogic.gdx.math.MathUtils;
import com.catinthedark.Constants;
import com.catinthedark.GameScore;
import com.catinthedark.entities.Entity;
import com.catinthedark.entities.House;

import java.util.List;
import java.util.Map;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com
 * Date: 26.04.14
 * Time: 14:31
 */
public class LevelGenerator {
    private static LevelGenerator ourInstance = new LevelGenerator();
    private float minDistance;
    private float maxDistance;

    public static LevelGenerator getInstance() {
        return ourInstance;
    }

    private LevelGenerator() {
    }

    public void generateLevel(Level level) {
        /*
         minDistance is between 2 * BUILDING_DISTANCE_MIN for minimum level of democracy
         and BUILDING_DISTANCE_MIN for maximum level of democracy
         */
        minDistance = (2f - ((float)GameScore.getInstance().getDemocracyLevel() /
                Constants.DEMOCRACY_LEVEL_MAX)) * Constants.BUILDING_DISTANCE_MIN;
        /*
         maxDistance is between 3 * BUILDING_DISTANCE_MIN for minimum level of democracy
         and 2 * BUILDING_DISTANCE_MIN for maximum level of democracy
         */
        maxDistance = (3f - ((float)GameScore.getInstance().getDemocracyLevel() /
                Constants.DEMOCRACY_LEVEL_MAX)) * Constants.BUILDING_DISTANCE_MIN;

        Map<Class, List<Entity>> entities = level.levelEntities;
        for (Class entityClass : entities.keySet()) {
            if (entityClass == House.class) {
                float maxHouseX = 0;
                float cameraPosition = level.gameScreen.getCamera().position.x;
                float rightEdgePosition = cameraPosition + level.gameScreen.getCamera().viewportWidth / 2f;

                for (Entity house : entities.get(entityClass)) {
                    if (house.getX() > maxHouseX) {
                        maxHouseX = house.getX();
                    }
                }

                if (rightEdgePosition - maxHouseX > minDistance) {
                    float newHouseX = rightEdgePosition + MathUtils.random(minDistance, maxDistance);
                    int heightInBlocks = MathUtils.random(2, 4);
                    int widthInBlocks = MathUtils.random(2, 3);
                    Entity houseEntity = new House(newHouseX, Constants.GROUND_LEVEL, widthInBlocks, heightInBlocks);
                    level.levelEntities.get(entityClass).add(houseEntity);
                }
            }
        }
    }
}
