package com.catinthedark.screens;

import com.catinthedark.BSODGame;

/**
 * Created by Ilya on 26.04.2014.
 */
public class GameScreen extends Basic2DScreen {
    public GameScreen(BSODGame game, int viewPortWidth, int viewPortHeight) {
        super(game, viewPortWidth, viewPortHeight);
        camera.position.set(viewPortWidth / 2f, viewPortHeight / 2f, 0);
        camera.update();
    }
}
