package com.catinthedark.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameHud {
	private static final int WIDTH = 850;
	private static final int HEIGHT = 30;
	private static final float BAR_BORDER_WIDTH = 2.0f;
	private static final Color BACKGROUND_COLOR = new Color(0.04f, 0.71f,
			0.24f, 1);
	private static final Color BAR_COLOR = Color.BLACK;

	private final Texture democracyTex;
	private final BitmapFont font;

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
		democracyTex = new Texture(
				Gdx.files.internal("texture/democracy_bar.png"));

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("font/impact.ttf"));
		FreeTypeFontParameter params = new FreeTypeFontParameter();
		params.size = 25;
		font = generator.generateFont(params);
		font.setColor(Color.RED);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
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
		this.health = health;
	}

	private void renderBar(int x, int y, int fillRate, Color background,
			Color barColor) {
		// bar background
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(background);
		shapeRenderer.rect(x, y, WIDTH, HEIGHT);
		shapeRenderer.end();

		// bar
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(barColor);
		shapeRenderer.rect(x + BAR_BORDER_WIDTH, y + BAR_BORDER_WIDTH,
				(float) fillRate / 100 * WIDTH - 2 * BAR_BORDER_WIDTH, HEIGHT
						- 2 * BAR_BORDER_WIDTH);
		shapeRenderer.end();
	}

	public void render() {

		Gdx.gl.glLineWidth(BAR_BORDER_WIDTH);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.MAGENTA);
		shapeRenderer.rect(X_POS + 130 + 3, Y_POS + BAR_BORDER_WIDTH,
				(float) democracyLevel / 100 * WIDTH - 6, HEIGHT - 4);
		shapeRenderer.end();

		spriteBatch.begin();
		font.draw(spriteBatch, "Democracy:", X_POS, Y_POS + 25);
		spriteBatch.end();
		spriteBatch.begin();
		spriteBatch.draw(democracyTex, X_POS + 130, Y_POS, WIDTH, HEIGHT);
		spriteBatch.end();

		spriteBatch.begin();
		font.draw(spriteBatch, "Health:", X_POS, Y_POS - 40 + 25);
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
				+ BAR_BORDER_WIDTH, (float) democracyLevel / 100 * 200 - 2
				* BAR_BORDER_WIDTH, HEIGHT - 2 * BAR_BORDER_WIDTH);
		shapeRenderer.end();

		// renderBar(X_POS, Y_POS, democracyLevel, BACKGROUND_COLOR, BAR_COLOR);
		// renderBar(X_POS + WIDTH + 20, Y_POS, health, BACKGROUND_COLOR,
		// Color.RED);
	}
}
