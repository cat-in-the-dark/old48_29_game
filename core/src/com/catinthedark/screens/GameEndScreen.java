package com.catinthedark.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.Constants;
import com.catinthedark.GameScore;
import com.catinthedark.assets.Assets;

public class GameEndScreen extends TitleScreen {

	private final SpriteBatch batch = new SpriteBatch();

	public GameEndScreen(ScreenChain chain, Texture texture, int timeToLive) {
		super(chain, texture, timeToLive);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		batch.begin();
		Assets.fontEnd.draw(batch, "Score:" + GameScore.getInstance().getScore(),
				Constants.VIEW_PORT_WIDTH / 2 * Constants.UNIT_SIZE - 70, Constants.VIEW_PORT_HEIGHT / 2 * Constants.UNIT_SIZE - 100);
		batch.end();
	}

}
