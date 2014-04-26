package com.catinthedark.screens;

import com.catinthedark.BSODGame;
import com.catinthedark.hud.GameHud;

/**
 * Created by Ilya on 26.04.2014.
 */
public class GameScreen extends Basic2DScreen {

	final GameHud hud;

	public GameScreen(BSODGame game, int viewPortWidth, int viewPortHeight) {
		super(game, viewPortWidth, viewPortHeight);

		this.hud = new GameHud();
		hud.conf().setX(10).setY(585);
		hud.setDemocracyLevel(100);
		hud.setHealth(40);

		camera.position.set(viewPortWidth / 2f, viewPortHeight / 2f, 0);
		camera.update();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		hud.render();
	}
}
