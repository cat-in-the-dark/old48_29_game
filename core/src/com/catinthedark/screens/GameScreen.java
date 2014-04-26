package com.catinthedark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.BSODGame;
import com.catinthedark.entities.President;
import com.catinthedark.hud.GameHud;
import com.catinthedark.level.Level;

/**
 * Created by Ilya on 26.04.2014.
 */
public class GameScreen extends Basic2DScreen {

	final GameHud hud;
    final Level level;
    final SpriteBatch batchMap;

	public GameScreen(BSODGame game, int viewPortWidth, int viewPortHeight) {
		super(game, viewPortWidth, viewPortHeight);

        batchMap = new SpriteBatch();
        level = new Level(this);

		this.hud = new GameHud();
		hud.conf().setX(10).setY(585);
		hud.setDemocracyLevel(100);
		hud.setHealth(40);

		camera.position.set(viewPortWidth / 2f, viewPortHeight / 2f, 0);
		camera.update();

        Gdx.input.setInputProcessor(this);
    }

    public Camera getCamera() {
        return camera;
    }

	@Override
	public void render(float delta) {
		super.render(delta);
        processKeys();
        batchMap.setProjectionMatrix(camera.combined);

        batchMap.begin();
        level.render(delta, batchMap);
        batchMap.end();
		hud.render();
	}

    public void processKeys() {
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            level.president.setDirection(President.Direction.RIGHT);
            level.president.move(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            level.president.setDirection(President.Direction.LEFT);
            level.president.move(true);
        } else {
            level.president.move(false);
        }
    }



    @Override
    public void dispose() {
        super.dispose();
        batchMap.dispose();
    }
}
