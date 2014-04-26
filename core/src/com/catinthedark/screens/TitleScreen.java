package com.catinthedark.screens;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.Constants;

public class TitleScreen extends Basic2DScreen {
	private final Texture texture;
	private final int timeToLive;
	private final boolean infinite;

	private final SpriteBatch spriteBatch = new SpriteBatch();
	private float timeLaps;
	private Map<Integer, Integer> bindingsMap = new HashMap<Integer, Integer>();
	public Bindings bindings = new Bindings();
	Integer nextHook = null;
	Integer prevHook = null;

	public class Bindings {
		public Bindings bind(int key, int index) {
			bindingsMap.put(key, index);
			return this;
		}

		public Bindings bindNext(int key) {
			nextHook = key;
			return this;
		}

		public Bindings bindPrev(int key) {
			prevHook = key;
			return this;
		}
	}

	/**
	 * 
	 * @param chain
	 * @param texture
	 * @param timeToLive
	 *            - ms
	 * @param fadeTime
	 *            - ms
	 */
	public TitleScreen(ScreenChain chain, Texture texture, int timeToLive) {
		super(chain);
		this.texture = texture;
		this.timeToLive = timeToLive;

		infinite = timeToLive == 0 ? true : false;
	}

	@Override
	public void render(float delta) {
		spriteBatch.begin();
		spriteBatch.enableBlending();
		spriteBatch.draw(texture, 0, 0, Constants.VIEW_PORT_WIDTH
				* Constants.UNIT_SIZE, Constants.VIEW_PORT_HEIGHT
				* Constants.UNIT_SIZE);
		spriteBatch.end();

		if (!infinite)
			timeLaps += delta;

		if (timeLaps * 1000 > timeToLive)
			next();

		for (Entry<Integer, Integer> bindPair : bindingsMap.entrySet())
			if (Gdx.input.isKeyPressed(bindPair.getKey()))
				gotoFrame(bindPair.getValue());

		if (nextHook != null)
			if (Gdx.input.isKeyPressed(nextHook))
				next();

		if (prevHook != null)
			if (Gdx.input.isKeyPressed(prevHook))
				prev();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		timeLaps = 0.0f;

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
