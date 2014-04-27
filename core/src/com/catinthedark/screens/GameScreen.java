package com.catinthedark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.Constants;
import com.catinthedark.GameScore;
import com.catinthedark.InterceptionManager;
import com.catinthedark.assets.Assets;
import com.catinthedark.entities.Entity;
import com.catinthedark.entities.President;
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
	final OrthographicCamera backCamera;
	final int[] layers = new int[] { 0 };

	public GameScreen(ScreenChain chain) {
		super(chain);

		backCamera = new OrthographicCamera(Constants.VIEW_PORT_WIDTH,
				Constants.VIEW_PORT_HEIGHT);
		backCamera.position.set(new float[] { Constants.VIEW_PORT_WIDTH / 2,
				Constants.VIEW_PORT_HEIGHT / 2, 0 });

		batchMap = new SpriteBatch();
		level = new Level(this);
		interManager = new InterceptionManager(level);

		this.hud = new GameHud();
		hud.conf().setX(10).setY(585);
		hud.setDemocracyLevel(70);
		hud.setHealth(40);

		camera.position.set(Constants.VIEW_PORT_WIDTH / 2f,
				Constants.VIEW_PORT_HEIGHT / 2f, 0);
		camera.update();
		backCamera.update();

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
		Assets.backgroundRenderer.render(layers);

		batchMap.setProjectionMatrix(camera.combined);

		batchMap.begin();
		level.render(delta, batchMap);
		batchMap.end();

		hud.setHealth(level.president.getHealth());
		hud.setDemocracyLevel(100 / Constants.DEMOCRACY_LEVEL_MAX
				* GameScore.getInstance().getDemocracyLevel());

		hud.render();
	}

	public void processKeys() {
		if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)
				|| Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
			level.shut(level.president);
			level.president.move(false, camera);
			return;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			level.placeOilFactory();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)
				|| Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			level.president.setDirection(Entity.Direction.RIGHT);
			level.president.move(true, camera);

			if (needMoveCamera()) {
				moveMainCamera();
				moveBackCamera();
				level.president.move(true, camera);
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)
				|| Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			level.president.setDirection(Entity.Direction.LEFT);
			level.president.move(true, camera);
		} else {
			level.president.move(false, camera);
		}

		// FIXME: only for debug
		if (Gdx.input.isKeyPressed(Input.Keys.G))
			next();
	}

	private void moveBackCamera() {

		final int vpw = Constants.VIEW_PORT_WIDTH;

		float backCamPos = backCamera.position.x;
		backCamPos += Constants.backCameraSpeed.x;
		if (backCamPos >= vpw / 2.0f + 2 * vpw)
			backCamPos = vpw / 2.0f;

		backCamera.position.set(backCamPos, backCamera.position.y,
				backCamera.position.z);

		backCamera.update();
	}

	private void moveMainCamera() {
		camera.position.set(camera.position.x + Constants.mainCameraSpeed.x,
				camera.position.y, camera.position.z);
		camera.update();
	}

	private boolean needMoveCamera() {
		return camera.position.x - level.president.getWidth()
				- Constants.maxPresidentDestinationFromBorder <= level.president
					.getX();
	}
}
