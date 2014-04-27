package com.catinthedark.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.catinthedark.assets.Assets;

public class GameHud {
	private static final int WIDTH = 850;
	private static final int HEIGHT = 30;
	private static final float BAR_BORDER_WIDTH = 2.0f;
	private static final Color BACKGROUND_COLOR = new Color(0.04f, 0.71f,
			0.24f, 1);

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

	public Configurator conf() {
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
		this.health = health <= 0 ? 0 : health;
	}

	public void render() {
		Gdx.gl.glLineWidth(BAR_BORDER_WIDTH);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(X_POS + 130 + 3, Y_POS + BAR_BORDER_WIDTH,
				(float) democracyLevel / 100 * WIDTH - 6, HEIGHT - 4);
		shapeRenderer.end();

		spriteBatch.begin();
		Assets.font.draw(spriteBatch, "Democracy:", X_POS, Y_POS + 25);
		spriteBatch.end();
		spriteBatch.begin();
		spriteBatch.draw(Assets.democracyTex, X_POS + 130, Y_POS, WIDTH, HEIGHT);
		spriteBatch.end();

		spriteBatch.begin();
		Assets.font.draw(spriteBatch, "Health:", X_POS, Y_POS - 40 + 25);
		spriteBatch.end();

		// bar background
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(BACKGROUND_COLOR);
		shapeRenderer.rect(X_POS + 130, Y_POS - 40, 200, 30);
		shapeRenderer.end();

		// bar
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(X_POS + 130 + BAR_BORDER_WIDTH, Y_POS - 40
				+ BAR_BORDER_WIDTH, (float) health / 100 * 200 - 2
				* BAR_BORDER_WIDTH, HEIGHT - 2 * BAR_BORDER_WIDTH);
		shapeRenderer.end();
	}
}
