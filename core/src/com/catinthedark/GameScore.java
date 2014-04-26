package com.catinthedark;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com
 * Date: 26.04.14
 * Time: 13:34
 */
public class GameScore {
    private static GameScore ourInstance = new GameScore();

    private int democracyLevel;
    private int health;

    public static GameScore getInstance() {
        return ourInstance;
    }

    private GameScore() {
        setDemocracyLevel(0);
        setHealth(Constants.HEALTH_MAX);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDemocracyLevel() {
        return democracyLevel;
    }

    public void setDemocracyLevel(int democracyLevel) {
        this.democracyLevel = democracyLevel;
    }
}
