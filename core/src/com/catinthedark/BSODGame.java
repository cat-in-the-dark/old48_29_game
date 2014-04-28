package com.catinthedark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.catinthedark.assets.Assets;
import com.catinthedark.screens.GameEndScreen;
import com.catinthedark.screens.GameScreen;
import com.catinthedark.screens.ScreenChain;
import com.catinthedark.screens.TitleScreen;

public class BSODGame extends Game {

	private TitleScreen createTutorialScreen(ScreenChain chain, Texture tex) {
		TitleScreen screen = new TitleScreen(chain, tex, 10000);
		screen.bindings.bindNext(Input.Keys.ENTER);

		return screen;
	}

	@Override
	public void create() {
		Assets.loadGameData();
		ScreenChain screenChain = new ScreenChain(this, 200);
		screenChain.add(new TitleScreen(screenChain, Assets.logoTex, 1000));

		TitleScreen startGame = new TitleScreen(screenChain,
				Assets.gameStartTex, 0);
		startGame.bindings.bindNext(Input.Keys.ENTER);
		screenChain.add(startGame);

		screenChain.add(createTutorialScreen(screenChain, Assets.tutorial1Tex));
		screenChain.add(createTutorialScreen(screenChain, Assets.tutorial2Tex));
		screenChain.add(createTutorialScreen(screenChain, Assets.tutorial3Tex));
		screenChain.add(createTutorialScreen(screenChain, Assets.tutorial4Tex));

		screenChain.add(new GameScreen(screenChain));

		GameEndScreen gameOverScreen = new GameEndScreen(screenChain,
				Assets.gameOverTex, 0);
		gameOverScreen.bindings.bind(Input.Keys.ESCAPE, 1);
		screenChain.add(gameOverScreen);
		
		GameEndScreen winScreen = new GameEndScreen(screenChain,Assets.gameWinTex, 0);
		winScreen.bindings.bind(Input.Keys.ESCAPE, 1);
		screenChain.add(winScreen);
		
		Assets.music.setVolume(0.7f);
		Assets.music.play();
		Assets.music.setLooping(true);
		
		screenChain.gotoFrame(0);
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
