package com.catinthedark.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.catinthedark.entities.HouseBlock;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Assets {
    public static Texture presidentTexture;
    public static Texture  democracyTex;
    public static Texture houseBlockTexture;
    public static BitmapFont font;

	

    public static void setupAssets() {
        Pixmap presidentPixmap = new Pixmap(5, 10, Pixmap.Format.RGBA8888);

        presidentPixmap.setColor(1, 0, 0, 1f);
        presidentPixmap.fill();
        presidentTexture = new Texture(presidentPixmap);
        
        democracyTex = new Texture(
    			Gdx.files.internal("texture/democracy_bar.png"));
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
    			Gdx.files.internal("font/impact.ttf"));
    	FreeTypeFontParameter params = new FreeTypeFontParameter();
    	params.size = 25;
    	font = generator.generateFont(params);
    	font.setColor(Color.RED);
    	generator.dispose(); // don't forget to dispose to avoid memory leaks!

        Pixmap houseBlockPixMap = new Pixmap(HouseBlock.blockWidth, HouseBlock.blockHeight, Pixmap.Format.RGBA8888);
        houseBlockPixMap.setColor(0, 1, 0, 1);
        houseBlockPixMap.fill();
        houseBlockTexture = new Texture(houseBlockPixMap);

    }

    public static void loadGameData() {
        setupAssets();
    }
}
