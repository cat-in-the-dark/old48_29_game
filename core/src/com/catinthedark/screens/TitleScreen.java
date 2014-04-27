package com.catinthedark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TitleScreen extends Basic2DScreen implements InputProcessor {
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
	 */
	public TitleScreen(ScreenChain chain, Texture texture, int timeToLive) {
		super(chain);
		this.texture = texture;
		this.timeToLive = timeToLive;

		infinite = timeToLive == 0 ? true : false;
	}

	@Override
	public boolean keyDown(int keycode) {
		System.out.println("pressed key:" + keycode);
		Integer nextFrame = bindingsMap.get(keycode);
		if (nextFrame != null)
			gotoFrame(nextFrame);

		if (nextHook != null)
			if (nextHook.equals(keycode)) {
				next();
				return true;
			}

		if (prevHook != null)
			if (prevHook.equals(keycode)) {
				prev();
				return true;
			}

		return true;
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
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		timeLaps = 0.0f;
		Gdx.input.setInputProcessor(this);

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
		spriteBatch.dispose();
		texture.dispose();
	}

}
