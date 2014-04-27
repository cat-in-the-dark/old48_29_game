package com.catinthedark.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.catinthedark.Constants;
import com.catinthedark.entities.HouseBlock;
import com.catinthedark.entities.President;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Assets {
    /**
     * Simple test textures
     */
    public static Texture presidentTexture;
    public static Texture democracyTex;
    public static Texture houseBlockTexture;
    public static Texture bulletTexture;

    /**
     * Screens' textures
     */
    public static Texture logoTex;
    public static Texture gameStartTex;
    public static Texture tutorial1Tex;
    public static Texture tutorial2Tex;
    public static Texture tutorial3Tex;
    public static Texture tutorial4Tex;
    public static Texture gameOverTex;

    public static BitmapFont font;
    public static TiledMap backgroundMap;
    public static TiledMapRenderer backgroundRenderer;
    public static Texture rocketTexture;
    public static Texture oilFactoryTexture;
    public static Texture oilFieldTexture;

    /**
     * Enemies(Windows with enemies) Textures and Animations
     */
    public static Texture enemyBlockTexture;
    public static TextureRegion leftUnbrocken;
    public static TextureRegion rightUnbrocken;
    public static TextureRegion leftTopUnbrocken;
    public static TextureRegion rightToppUnbrocken;
    public static TextureRegion leftBrocken;
    public static TextureRegion rightBrocken;
    public static Animation mdIdle;
    public static Animation mdShoot;

    /**
     * President Textures and Animations
     */
    public static Texture presidentSheet;
    public static Animation presidentRunRight;
    public static Animation presidentRunLeft;
    public static Animation presidentIdle;


    public static void setupAssets() {
        /**
         * It's just text textures!!
         */
        Pixmap presidentPixmap = new Pixmap(5, 10, Pixmap.Format.RGBA8888);
        Pixmap rocketPixmap = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
        Pixmap bulletPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        Pixmap oilFactoryPixMap = new Pixmap(2, 4, Pixmap.Format.RGBA8888);
        Pixmap oilFieldPixmap = new Pixmap(7, 2, Pixmap.Format.RGBA8888);

        presidentPixmap.setColor(1, 0, 0, 1f);
        presidentPixmap.fill();
        presidentTexture = new Texture(presidentPixmap);

        rocketPixmap.setColor(0, 1, 0, 1f);
        rocketPixmap.fill();
        rocketTexture = new Texture(rocketPixmap);
        
        bulletPixmap.setColor(0,0,1,1.f);
        bulletPixmap.fill();
        bulletTexture = new Texture(bulletPixmap);

        oilFactoryPixMap.setColor(0,0,0,1f);
        oilFactoryPixMap.fill();
        oilFactoryTexture = new Texture(oilFactoryPixMap);

        oilFieldPixmap.setColor(0, 0, 0, 1f);
        oilFieldPixmap.fill();
        oilFieldTexture = new Texture(oilFieldPixmap);

        Pixmap houseBlockPixMap = new Pixmap(HouseBlock.blockWidth, HouseBlock.blockHeight, Pixmap.Format.RGBA8888);
        houseBlockPixMap.setColor(0, 1, 1, 1);
        houseBlockPixMap.fill();
        houseBlockTexture = new Texture(houseBlockPixMap);

        Pixmap enemyBlockPixMap = new Pixmap(HouseBlock.blockWidth, HouseBlock.blockHeight, Pixmap.Format.RGBA8888);
        enemyBlockPixMap.setColor(0, 0, 1, 1);
        enemyBlockPixMap.fill();
        //-> End of test textures

        
        democracyTex = new Texture(
    			Gdx.files.internal("texture/democracy_bar.png"));

        logoTex = new Texture(
    			Gdx.files.internal("texture/logo.png"));
        gameStartTex =  new Texture(
    			Gdx.files.internal("texture/game_start_page.png"));
        tutorial1Tex =  new Texture(
    			Gdx.files.internal("texture/tutorial1.png"));
        tutorial2Tex =  new Texture(
    			Gdx.files.internal("texture/tutorial2.png"));
        tutorial3Tex =  new Texture(
    			Gdx.files.internal("texture/tutorial3.png"));
        tutorial4Tex =  new Texture(
    			Gdx.files.internal("texture/tutorial4.png"));
        gameOverTex =  new Texture(
    			Gdx.files.internal("texture/game_over.png"));

        enemyBlockTexture = new Texture(
                Gdx.files.internal("texture/houses_shahids.png"));
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
    			Gdx.files.internal("font/impact.ttf"));

        presidentSheet = new Texture(
                Gdx.files.internal("texture/president_going.png"));

    	FreeTypeFontParameter params = new FreeTypeFontParameter();
    	params.size = 25;
    	font = generator.generateFont(params);
    	font.setColor(Color.RED);
    	generator.dispose(); // don't forget to dispose to avoid memory leaks!
    	
    	backgroundMap = new TmxMapLoader().load("area02.tmx");
    	backgroundRenderer = new OrthogonalTiledMapRenderer(backgroundMap, 1/32f);
    }

    public static void loadGameData() {
        setupAssets();
        initAnimation();
    }

    private static void initAnimation() {
        TextureRegion[][] framesEnemy = TextureRegion.split(Assets.enemyBlockTexture,
                Assets.enemyBlockTexture.getWidth() / Constants.FRAME_COLS,
                Assets.enemyBlockTexture.getHeight() / Constants.FRAME_ROWS);

        leftUnbrocken = framesEnemy[6][0];
        rightUnbrocken = framesEnemy[6][1];
        leftTopUnbrocken = framesEnemy[4][0];
        rightToppUnbrocken = framesEnemy[4][1];
        leftBrocken = framesEnemy[5][0];
        rightBrocken = framesEnemy[5][1];
        mdIdle = new Animation(0, framesEnemy[3][3]);
        mdShoot = new Animation(Constants.ANIMATION_SPEED, new TextureRegion[]{
                framesEnemy[3][3], framesEnemy[4][3]
        });
        mdShoot.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[][] presidentFrames = TextureRegion.split(Assets.presidentSheet, President.WIDTH * Constants.TILE_WIDTH, President.HEIGHT * Constants.TILE_HEIGHT);

        presidentRunRight = new Animation(Constants.ANIMATION_SPEED, new TextureRegion[] {
                presidentFrames[0][0], presidentFrames[0][1], presidentFrames[0][2], presidentFrames[0][3], presidentFrames[0][4], presidentFrames[0][5], presidentFrames[0][6], presidentFrames[0][7]
        });
        presidentRunRight.setPlayMode(Animation.PlayMode.LOOP);

        presidentRunLeft = new Animation(Constants.ANIMATION_SPEED, new TextureRegion[] {
                presidentFrames[0][7], presidentFrames[0][6], presidentFrames[0][5], presidentFrames[0][4], presidentFrames[0][3], presidentFrames[0][2], presidentFrames[0][1], presidentFrames[0][0]
        });
        presidentRunLeft.setPlayMode(Animation.PlayMode.LOOP);

        presidentIdle =  new Animation(Constants.ANIMATION_SPEED, new TextureRegion[]{
                presidentFrames[0][0]
        });
        presidentIdle.setPlayMode(Animation.PlayMode.NORMAL);
    }
}
