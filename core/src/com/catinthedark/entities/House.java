package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.catinthedark.Constants;
import com.catinthedark.GameScore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com
 * Date: 26.04.14
 * Time: 12:52
 */
public class House extends Entity{

    private int heightInBlocks;
    private int widthInBlocks;
    private ArrayList<HouseBlock> houseBlocks = new ArrayList<HouseBlock>();

    public House(float x, float y, int widthInBlocks, int heightInBlocks) {
        super(x, y, widthInBlocks * HouseBlock.blockWidth, heightInBlocks * HouseBlock.blockHeight);
        this.x = x;
        this.y = y;
        this.widthInBlocks = widthInBlocks;
        this.heightInBlocks = heightInBlocks;
        buildHouse();
    }

    private void buildHouse() {
        int blocksCount = widthInBlocks * heightInBlocks;
        int enemiesInHouseMax = GameScore.getInstance().getDemocracyLevel() * blocksCount / Constants.DEMOCRACY_LEVEL_MAX;
        int enemiesInHouse = MathUtils.random(0, enemiesInHouseMax);

        List<Integer> vacantRoomNumbers = new ArrayList<Integer>();
        for (int i = 0; i < blocksCount; i++) {
            vacantRoomNumbers.add(i);
        }
        List<Boolean> roomWithEnemy = (Arrays.asList(new Boolean[blocksCount]));
        Collections.fill(roomWithEnemy, Boolean.FALSE);

        for (int i = 0; i < enemiesInHouse; i ++) {
            int roomNumber = MathUtils.random(0, vacantRoomNumbers.size() - 1);
            roomWithEnemy.set(vacantRoomNumbers.get(roomNumber), true);
            vacantRoomNumbers.remove(roomNumber);
        }

        for (int i = 0; i < widthInBlocks; i ++) {
            for (int j = 0; j < heightInBlocks; j ++) {
                float blockX = i * HouseBlock.blockWidth + this.x;
                float blockY = j * HouseBlock.blockHeight + this.y;

                boolean left = (i == 0);
                boolean top = (j == heightInBlocks - 1);

                houseBlocks.add(new HouseBlock(roomWithEnemy.get(i + j * widthInBlocks), top, left, blockX, blockY));
                vacantRoomNumbers.add(i + j);
            }
        }
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        for (HouseBlock block: houseBlocks) {
            block.render(delta, batch);
        }
    }
}
