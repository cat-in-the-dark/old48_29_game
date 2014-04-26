package com.catinthedark.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameHud {
	private int democracyLevel;
	private int health;
	
	private static final int X_POS = 0;
	private static final int Y_POS = 0;

	private final SpriteBatch spriteBatch = new SpriteBatch();
	private final ShapeRenderer shapeRenderer = new ShapeRenderer();

	public GameHud() {
	}

	public int getDemocracyLevel() {
		return democracyLevel;
	}

	public void setDemocracyLevel(int democracyLevel) {
		this.democracyLevel = democracyLevel;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void draw(){
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.rect(0,0,100,10);
		shapeRenderer.end();
	}

}
