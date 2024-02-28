package com.catinthedark.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.catinthedark.BSODGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Beneath the surface of the democracy");
		config.setWindowedMode(1024, 640);
		new Lwjgl3Application(new BSODGame(), config);
	}
}
