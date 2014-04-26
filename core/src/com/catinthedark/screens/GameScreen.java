package com.catinthedark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.BSODGame;
import com.catinthedark.InterceptionManager;
import com.catinthedark.assets.Assets;
import com.catinthedark.entities.Entity;
import com.catinthedark.hud.GameHud;
import com.catinthedark.level.Level;

/**
 * Created by Ilya on 26.04.2014.
 */
public class GameScreen extends Basic2DScreen {

	final GameHud hud;
	final Level level;
	final InterceptionManager interManager;
	final SpriteBatch batchMap;

	public GameScreen(BSODGame game, int viewPortWidth, int viewPortHeight) {
		super(game, viewPortWidth, viewPortHeight);

		batchMap = new SpriteBatch();
		level = new Level(this);
		interManager = new InterceptionManager(level);

		this.hud = new GameHud();
		hud.conf().setX(10).setY(585);
		hud.setDemocracyLevel(70);
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
		interManager.manage();
		
		// draw background image
		Assets.backgroundRenderer.setView(backCamera);
		Assets.backgroundRenderer.render(new int[] { 0 });

		batchMap.setProjectionMatrix(camera.combined);

		batchMap.begin();
		level.render(delta, batchMap);
		batchMap.end();
		hud.render();

	}

	
    public void processKeys() {
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            level.shut(level.president);
            level.president.move(false);
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            level.president.setDirection(Entity.Direction.RIGHT);
            level.president.move(true);
            
            float backCamPos = backCamera.position.x;
			backCamPos += 0.2;
			if (backCamPos >= viewPortWidth / 2.0f + 2*viewPortWidth)
				backCamPos = viewPortWidth / 2.0f;

			backCamera.position.set(backCamPos, backCamera.position.y,
					backCamera.position.z);
			backCamera.update();
			
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            level.president.setDirection(Entity.Direction.LEFT);
            level.president.move(true);
        } else {
            level.president.move(false);
        }
}
}
