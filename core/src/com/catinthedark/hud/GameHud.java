package com.catinthedark.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameHud {
	private int democracyLevel;
	private int health;

	private int X_POS = 0;
	private int Y_POS = 0;

	private final SpriteBatch spriteBatch = new SpriteBatch();
	private final ShapeRenderer shapeRenderer = new ShapeRenderer();
	private final Configurator conf = new Configurator();

	public class Configurator {
		public Configurator setX(int x) {
			X_POS = x;
			return this;
		}

		public Configurator setY(int y) {
			Y_POS = y;
			return this;
		}
	}

	public GameHud() {
	}
	
	public Configurator conf(){
		return conf;
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

	public void draw() {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.rect(X_POS, Y_POS, 100, 10);
		shapeRenderer.end();
	}

}
