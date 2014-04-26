package com.catinthedark.screens;

import com.catinthedark.BSODGame;
import com.catinthedark.hud.GameHud;

/**
 * Created by Ilya on 26.04.2014.
 */
public class GameScreen extends Basic2DScreen {
	
	final GameHud hud = new GameHud();
	
    public GameScreen(BSODGame game, int viewPortWidth, int viewPortHeight) {
        super(game, viewPortWidth, viewPortHeight);
        camera.position.set(viewPortWidth / 2f, viewPortHeight / 2f, 0);
        camera.update();
    }
    
    @Override
    public void render(float delta) {
    	super.render(delta);
    	
    	hud.draw();
    }
}
