package com.catinthedark;

import com.badlogic.gdx.Game;
import com.catinthedark.screens.GameScreen;

public class BSODGame extends Game {
	@Override
	public void create () {
        setScreen(new GameScreen(this, 32, 20));
	}

	@Override
	public void dispose () {
        super.dispose();
	}
}
