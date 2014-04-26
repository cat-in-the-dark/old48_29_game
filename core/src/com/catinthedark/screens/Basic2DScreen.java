package com.catinthedark.screens;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.catinthedark.BSODGame;

/**
 * Created by Ilya on 26.04.2014.
 */
public abstract class Basic2DScreen  extends InputAdapter implements Screen {
    protected BSODGame game;
    protected final OrthographicCamera camera;

    public Basic2DScreen(BSODGame game) {
        this.game = game;
        this.camera = new OrthographicCamera(32, 20);
    }

    @Override
    public void render(float delta) {

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
