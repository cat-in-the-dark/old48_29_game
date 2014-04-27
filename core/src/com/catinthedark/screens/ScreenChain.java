package com.catinthedark.screens;

import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.List;

public class ScreenChain {
	private List<Basic2DScreen> screens = new ArrayList<Basic2DScreen>();
	private final Game game;
	private int currentFrame = 0;
	private final int switchDelay;

	public ScreenChain(Game game, int switchDelay) {
		this.game = game;
		this.switchDelay = switchDelay;
	}

	public void add(Basic2DScreen screen) {
		screens.add(screen);
	}

	public void next() {
		try {
			//Thread.sleep(switchDelay);
			currentFrame++;
			game.setScreen(screens.get(currentFrame));
		} catch (Exception ex) {
			throw new RuntimeException("next frame does not exists! AZAZA!");
		}

	}

	public void prev() {

		try {
			//Thread.sleep(switchDelay)
			currentFrame--;
			game.setScreen(screens.get(currentFrame));
		} catch (Exception ex) {
			throw new RuntimeException("prev frame does not exists! AZAZA!");
		}
	}

	public void gotoFrame(int index) {
		currentFrame = index;
		try {
			//Thread.sleep(switchDelay);
			game.setScreen(screens.get(currentFrame));
		} catch (Exception ex) {
			throw new RuntimeException("frame with id '" + index
					+ "' does not exists! AZAZA!");
		}
	}

	public int getCurrentFrame() {
		return currentFrame;
	}
}
