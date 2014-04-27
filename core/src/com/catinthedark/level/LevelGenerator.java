package com.catinthedark.level;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.catinthedark.Constants;
import com.catinthedark.GameScore;
import com.catinthedark.entities.*;

import java.util.List;
import java.util.Map;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com
 * Date: 26.04.14
 * Time: 14:31
 */
public class LevelGenerator {
    private static LevelGenerator ourInstance = new LevelGenerator();
    private float minHouseDistance;
    private float maxHouseDistance;
    private float minOilFieldDistance;
    private float maxOilFieldDistance;

    private long lastTntVehicle;
    private int tntVehicleIntervalMax;
    private int tntVehicleIntervalMin = Constants.TNT_VEHICLE_INTERVAL_MIN;
    private long tntVehicleInterval = Constants.TNT_VEHICLE_INTERVAL_MAX * 1000000000L;

    private long lastAidVehicle;
    private int aidVehicleIntervalMax;
    private int aidVehicleIntervalMin = Constants.AID_VEHICLE_INTERVAL_MIN;
    private long aidVehicleInterval = Constants.AID_VEHICLE_INTERVAL_MAX * 1000000000L;

    public static LevelGenerator getInstance() {
        return ourInstance;
    }

    private LevelGenerator() {
        lastTntVehicle = lastAidVehicle = TimeUtils.nanoTime();
    }

    public void generateLevel(Level level) {
        /**
         * minHouseDistance is between 2 * BUILDING_DISTANCE_MIN for minimum level of democracy
         * and BUILDING_DISTANCE_MIN for maximum level of democracy
         */
        minHouseDistance = (2f - ((float)GameScore.getInstance().getDemocracyLevel() /
                Constants.DEMOCRACY_LEVEL_MAX)) * Constants.BUILDING_DISTANCE_MIN;
        /**
         * maxHouseDistance is between 3 * BUILDING_DISTANCE_MIN for minimum level of democracy
         * and 2 * BUILDING_DISTANCE_MIN for maximum level of democracy
         */
        maxHouseDistance = (3f - ((float)GameScore.getInstance().getDemocracyLevel() /
                Constants.DEMOCRACY_LEVEL_MAX)) * Constants.BUILDING_DISTANCE_MIN;

        minOilFieldDistance = Constants.OIL_FIELD_DISTANCE_MIN;
        maxOilFieldDistance = Constants.OIL_FIELD_DISTANCE_MAX;

        Map<Class, List<Entity>> entities = level.levelEntities;
        for (Class entityClass : entities.keySet()) {
            if (entityClass == House.class) {
                List<Entity> houses = entities.get(entityClass);
                generateHouse(level, houses);
            } else if (entityClass == OilField.class) {
                List<Entity> oilFields = entities.get(entityClass);
                generateOilField(level, oilFields);
            } else if (entityClass == TntVehicle.class) {
                generateTntVehicle(level);
            } else if (entityClass == AidVehicle.class) {
                generateAidVehicle(level);
            }
        }
    }

    private void generateAidVehicle(Level level) {
        aidVehicleIntervalMax = Constants.AID_VEHICLE_INTERVAL_MAX +
                ((Constants.DEMOCRACY_LEVEL_MAX - GameScore.getInstance().getDemocracyLevel()) / 2);

        aidVehicleIntervalMin = Constants.AID_VEHICLE_INTERVAL_MIN +
                ((Constants.DEMOCRACY_LEVEL_MAX - GameScore.getInstance().getDemocracyLevel()) / 2);

        float cameraPosition = level.gameScreen.getCamera().position.x;
        float leftEdgePosition = cameraPosition - level.gameScreen.getCamera().viewportWidth / 2f;

        if (TimeUtils.nanoTime() - aidVehicleInterval > lastAidVehicle) {
            Entity aidVehicle = new AidVehicle(leftEdgePosition - AidVehicle.width, Constants.GROUND_LEVEL,
                    1 * Constants.AID_VEHICLE_SPEED_X, 0);
            level.levelEntities.get(AidVehicle.class).add(aidVehicle);
            lastAidVehicle = TimeUtils.nanoTime();
            aidVehicleInterval = MathUtils.random(aidVehicleIntervalMin, aidVehicleIntervalMax) * 1000000000L;
        }
    }

    private void generateTntVehicle(Level level) {

        tntVehicleIntervalMax = Constants.TNT_VEHICLE_INTERVAL_MAX +
                ((Constants.DEMOCRACY_LEVEL_MAX - GameScore.getInstance().getDemocracyLevel()) / 2);

        tntVehicleIntervalMin = Constants.TNT_VEHICLE_INTERVAL_MIN +
                ((Constants.DEMOCRACY_LEVEL_MAX - GameScore.getInstance().getDemocracyLevel()) / 2);

        float cameraPosition = level.gameScreen.getCamera().position.x;
        float rightEdgePosition = cameraPosition + level.gameScreen.getCamera().viewportWidth / 2f;


        if (TimeUtils.nanoTime() - tntVehicleInterval > lastTntVehicle) {
            Entity tntVehicle = new TntVehicle(rightEdgePosition, Constants.GROUND_LEVEL,
                    -1 * Constants.TNT_VEHICLE_SPEED_X, 0);
            level.levelEntities.get(TntVehicle.class).add(tntVehicle);
            lastTntVehicle = TimeUtils.nanoTime();
            tntVehicleInterval = MathUtils.random(tntVehicleIntervalMin, tntVehicleIntervalMax) * 1000000000L;
        }
    }

    private void generateOilField(Level level, List<Entity> oilFields) {
        float maxFieldX = 0;
        float cameraPosition = level.gameScreen.getCamera().position.x;
        float rightEdgePosition = cameraPosition + level.gameScreen.getCamera().viewportWidth / 2f;

        for (Entity oilField : oilFields) {
            if (oilField.getX() > maxFieldX) {
                maxFieldX = oilField.getX();
            }
        }

        if (rightEdgePosition - maxFieldX > minOilFieldDistance) {
            float newOilFieldX = rightEdgePosition + MathUtils.random(minOilFieldDistance, maxOilFieldDistance);
            float oilFieldY = Constants.OIL_FIELD_Y_POS;
            Entity oilFieldEntity = new OilField(newOilFieldX, oilFieldY);
            level.levelEntities.get(OilField.class).add(oilFieldEntity);
        }
    }

    private void generateHouse(Level level, List<Entity> houses) {
        float maxHouseX = 0;
        float cameraPosition = level.gameScreen.getCamera().position.x;
        float rightEdgePosition = cameraPosition + level.gameScreen.getCamera().viewportWidth / 2f;

        for (Entity house : houses) {
            if (house.getX() > maxHouseX) {
                maxHouseX = house.getX();
            }
        }

        if (rightEdgePosition - maxHouseX > minHouseDistance) {
            float newHouseX = rightEdgePosition + MathUtils.random(minHouseDistance, maxHouseDistance);
            int heightInBlocks = MathUtils.random(2, 4);
            int widthInBlocks = 2;
            Entity houseEntity = new House(level, newHouseX, Constants.GROUND_LEVEL, widthInBlocks, heightInBlocks);
            level.levelEntities.get(House.class).add(houseEntity);
        }
    }
}
