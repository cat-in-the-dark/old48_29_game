package com.catinthedark.assets;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Assets {
    public static Texture presidentTexture;

    public static void setupAssets() {
        Pixmap presidentPixmap = new Pixmap(5, 10, Pixmap.Format.RGBA8888);

        presidentPixmap.setColor(1, 0, 0, 1f);
        presidentPixmap.fill();
        presidentTexture = new Texture(presidentPixmap);
    }

    public static void loadGameData() {
        setupAssets();
    }
}
