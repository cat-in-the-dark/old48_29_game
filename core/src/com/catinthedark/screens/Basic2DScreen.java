package com.catinthedark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.catinthedark.Constants;

/**
 * Created by Ilya on 26.04.2014.
 */
public abstract class Basic2DScreen extends InputAdapter implements Screen {
	protected ScreenChain chain;
	protected final OrthographicCamera camera;

	public Basic2DScreen(ScreenChain chain) {
		this.chain = chain;
		this.camera = new OrthographicCamera(Constants.VIEW_PORT_WIDTH, Constants.VIEW_PORT_HEIGHT);
	}
	
	protected int getCurrentFrame () {
		return chain.getCurrentFrame();
	}
	
	protected void next(){
		chain.next();
	}
	protected void prev(){
		chain.prev();
	}
	protected void gotoFrame(int index){
		chain.gotoFrame(index);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
