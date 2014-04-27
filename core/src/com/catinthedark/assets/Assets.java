package com.catinthedark.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
import com.catinthedark.entities.TntVehicle;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Assets {
    /**
     * Simple test textures
     */
    public static Texture democracyTex;
    public static Texture rocketTexture;

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

    public static Texture oilFieldTexture;
    public static Texture tntVehicleTexture;
    public static Texture houseBlockTexture;

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
    public static Texture bulletTexture;

    /**
     * President Textures and Animations
     */
    public static Texture presidentSheet;
    public static Animation presidentRunRight;
    public static Animation presidentRunLeft;
    public static Animation presidentIdle;

    /**
     * Oil factory textures and animations
     */
    public static Texture oilFactoryTexture;
    public static Animation oilFactoryAppearance;
    
    /**
     * sounds
     */
    public static Sound explosionSound;
    public static Sound smallExpSound;
    public static Sound oh1Sound;
    public static Sound oh2Sound;

    /**
     * Oil field textures and animations
     */
    public static Texture oilFieldSheet;
    public static Animation oilFieldAnimation;


    public static void setupAssets() {
        /**
         * It's just text textures!!
         */
        Pixmap rocketPixmap = new Pixmap(2, 2, Pixmap.Format.RGBA8888);

        rocketPixmap.setColor(0, 1, 0, 1f);
        rocketPixmap.fill();
        rocketTexture = new Texture(rocketPixmap);

        Pixmap oilFieldPixmap = new Pixmap(7, 2, Pixmap.Format.RGBA8888);
        oilFieldPixmap.setColor(0, 0, 0, 1f);
        oilFieldPixmap.fill();
        oilFieldTexture = new Texture(oilFieldPixmap);

        Pixmap houseBlockPixMap = new Pixmap(HouseBlock.blockWidth, HouseBlock.blockHeight, Pixmap.Format.RGBA8888);
        houseBlockPixMap.setColor(0, 1, 1, 1);
        houseBlockPixMap.fill();
        houseBlockTexture = new Texture(houseBlockPixMap);

        Pixmap tntVehiclePixmap = new Pixmap(TntVehicle.width, TntVehicle.height, Pixmap.Format.RGBA8888);
        tntVehiclePixmap.setColor(0, 0.5f, 0, 1.f);
        tntVehiclePixmap.fill();
        tntVehicleTexture = new Texture(tntVehiclePixmap);

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
        presidentSheet = new Texture(
                Gdx.files.internal("texture/president_going.png"));
        oilFactoryTexture = new Texture(
                Gdx.files.internal("texture/vyshka.png"));
        oilFieldSheet = new Texture(
                Gdx.files.internal("texture/oil.png"));
        bulletTexture = new Texture(
                Gdx.files.internal("texture/bullet.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("font/impact.ttf"));
        FreeTypeFontParameter params = new FreeTypeFontParameter();
        params.size = 25;
        font = generator.generateFont(params);
    	font.setColor(Color.RED);
    	generator.dispose(); // don't forget to dispose to avoid memory leaks!
    	
    	backgroundMap = new TmxMapLoader().load("area02.tmx");
    	backgroundRenderer = new OrthogonalTiledMapRenderer(backgroundMap, 1/32f);
    	
    	explosionSound = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));
    	smallExpSound = Gdx.audio.newSound(Gdx.files.internal("sound/small_explosion.wav"));
    	oh1Sound = Gdx.audio.newSound(Gdx.files.internal("sound/oh1.mp3"));
    	oh2Sound = Gdx.audio.newSound(Gdx.files.internal("sound/oh2.mp3"));
    }

    public static void loadGameData() {
        setupAssets();
        initAnimation();
    }

    private static void initAnimation() {
        TextureRegion[][] framesEnemy = TextureRegion.split(
                Assets.enemyBlockTexture,
                Assets.enemyBlockTexture.getWidth() / Constants.FRAME_COLS,
                Assets.enemyBlockTexture.getHeight() / Constants.FRAME_ROWS);
        TextureRegion[][] presidentFrames = TextureRegion.split(
                Assets.presidentSheet,
                President.WIDTH * Constants.TILE_WIDTH,
                President.HEIGHT * Constants.TILE_HEIGHT);
        TextureRegion[][] framesOilFactory = TextureRegion.split(
                Assets.oilFactoryTexture,
                Assets.oilFactoryTexture.getWidth() / 4,
                Assets.oilFactoryTexture.getHeight());
        TextureRegion[][] framesOilField = TextureRegion.split(Assets.oilFieldSheet,
                Assets.oilFieldSheet.getWidth() / 2,
                Assets.oilFieldSheet.getHeight());

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

        presidentRunRight = new Animation(Constants.ANIMATION_SPEED, new TextureRegion[] {
                presidentFrames[0][0],
                presidentFrames[0][1],
                presidentFrames[0][2],
                presidentFrames[0][3],
                presidentFrames[0][4],
                presidentFrames[0][5],
                presidentFrames[0][6],
                presidentFrames[0][7]
        });
        presidentRunRight.setPlayMode(Animation.PlayMode.LOOP);

        presidentRunLeft = new Animation(Constants.ANIMATION_SPEED, new TextureRegion[] {
                presidentFrames[0][7],
                presidentFrames[0][6],
                presidentFrames[0][5],
                presidentFrames[0][4],
                presidentFrames[0][3],
                presidentFrames[0][2],
                presidentFrames[0][1],
                presidentFrames[0][0]
        });
        presidentRunLeft.setPlayMode(Animation.PlayMode.LOOP);

        presidentIdle =  new Animation(Constants.ANIMATION_SPEED, new TextureRegion[]{
                presidentFrames[0][1]
        });
        presidentIdle.setPlayMode(Animation.PlayMode.NORMAL);

        oilFactoryAppearance = new Animation(Constants.ANIMATION_SPEED, new TextureRegion[] {
                framesOilFactory[0][0],
                framesOilFactory[0][1],
                framesOilFactory[0][2],
                framesOilFactory[0][3]
        });
        oilFactoryAppearance.setPlayMode(Animation.PlayMode.NORMAL);

        oilFieldAnimation = new Animation(Constants.ANIMATION_SPEED_SLOW, new TextureRegion[]{
                framesOilField[0][0],
                framesOilField[0][1]
        });
        oilFieldAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
}
