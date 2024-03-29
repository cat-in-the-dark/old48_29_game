package com.catinthedark.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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
import com.catinthedark.entities.President;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Assets {
    public static Texture democracyTex;

    /**
     * Rocket texture
     */
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
    public static Texture gameWinTex;

    public static BitmapFont font;
    public static BitmapFont fontEnd;
    public static TiledMap backgroundMap;
    public static TiledMapRenderer backgroundRenderer;

    public static Texture oilFieldTexture;
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
    public static Animation<TextureRegion> mdIdle;
    public static Animation<TextureRegion> mdShoot;
    public static Texture bulletTexture;

    /**
     * President Textures and Animations
     */
    public static Texture presidentSheet;
    public static Animation<TextureRegion> presidentRunRight;
    public static Animation<TextureRegion> presidentRunLeft;
    public static Animation<TextureRegion> presidentIdle;
    public static Animation<TextureRegion> presidentAimUp;
    public static Animation<TextureRegion> presidentAimDown;
    public static Animation<TextureRegion> presidentAimMiddle;

    /**
     * Oil factory textures and animations
     */
    public static Texture oilFactoryTexture;
    public static Animation<TextureRegion> oilFactoryAppearance;
    public static Animation<TextureRegion> oilFactoryDisappearance;
    
    /**
     * sounds
     */
    public static Sound explosionSound;
    public static Sound smallExpSound;
    public static Sound oh1Sound;
    public static Sound oh2Sound;
    public static Sound kamazExpSound;
    public static Music music;

    /**
     * Oil field textures and animations
     */
    public static Texture oilFieldSheet;
    public static Animation<TextureRegion> oilFieldAnimation;

    /**
     * Tnt vehicle textures and animation
     */
    public static Texture tntVehicleTexture;
    public static Animation<TextureRegion> tntVehicleRiding;
    public static TextureRegion tntVehicleExploded;

    /**
     * Aid vehicle textures and animation
     */
    public static Texture aidVehicleTexture;
    public static Animation<TextureRegion> aidVehicleRiding;
    public static TextureRegion aidVehicleExploded;

    /**
     * Explosion textures and animation
     */
    public static Texture explosionSheet;
    public static Animation<TextureRegion> explosionAnimation;

    /**
     * Hint textures
     */
    public static Texture oilHintTexture;
    //public static Texture aidHintTexture;

    public static void setupAssets() {
        democracyTex = new Texture(
    			Gdx.files.internal("texture/democracy_bar.png"));
        logoTex = new Texture(
    			Gdx.files.internal("texture/logo.png"));
        gameStartTex =  new Texture(
    			Gdx.files.internal("texture/game_start_page.png"));
        switch (Constants.DEFAULT_LANG) {
            case EN:
                tutorial1Tex =  new Texture(
                        Gdx.files.internal("texture/tutorial1_en.png"));
                tutorial2Tex =  new Texture(
                        Gdx.files.internal("texture/tutorial2_en.png"));
                tutorial3Tex =  new Texture(
                        Gdx.files.internal("texture/tutorial3_en.png"));
                tutorial4Tex =  new Texture(
                        Gdx.files.internal("texture/tutorial4_en.png"));
                oilHintTexture = new Texture(
                        Gdx.files.internal("texture/oil_hint_en.png"));
                break;
            case RU:
                tutorial1Tex =  new Texture(
                        Gdx.files.internal("texture/tutorial1.png"));
                tutorial2Tex =  new Texture(
                        Gdx.files.internal("texture/tutorial2.png"));
                tutorial3Tex =  new Texture(
                        Gdx.files.internal("texture/tutorial3.png"));
                tutorial4Tex =  new Texture(
                        Gdx.files.internal("texture/tutorial4.png"));
                oilHintTexture = new Texture(
                        Gdx.files.internal("texture/oil_hint.png"));
                break;
        }
        gameOverTex =  new Texture(
    			Gdx.files.internal("texture/game_over.png"));
        gameWinTex =  new Texture(
    			Gdx.files.internal("texture/win_screen.png"));
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
        tntVehicleTexture = new Texture(
                Gdx.files.internal("texture/kamaz_TERRORISM.png"));
        aidVehicleTexture = new Texture(
                Gdx.files.internal("texture/kamaz_AID.png"));
        explosionSheet = new Texture(
                Gdx.files.internal("texture/explosion.png"));
        rocketTexture = new Texture(
                Gdx.files.internal("texture/snaryad.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("font/impact.ttf"));
        FreeTypeFontParameter params = new FreeTypeFontParameter();
        params.size = 25;
        font = generator.generateFont(params);
    	font.setColor(Color.RED);
    	
    	params.size = 50;
    	
    	fontEnd = generator.generateFont(params);
    	fontEnd.setColor(Color.WHITE);
    	generator.dispose(); // don't forget to dispose to avoid memory leaks!
    	
    	backgroundMap = new TmxMapLoader().load("area02.tmx");
    	backgroundRenderer = new OrthogonalTiledMapRenderer(backgroundMap, 1/32f);
    	
    	explosionSound = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));
    	smallExpSound = Gdx.audio.newSound(Gdx.files.internal("sound/small_explosion.wav"));
    	oh1Sound = Gdx.audio.newSound(Gdx.files.internal("sound/oh1.mp3"));
    	oh2Sound = Gdx.audio.newSound(Gdx.files.internal("sound/oh2.mp3"));
    	kamazExpSound = Gdx.audio.newSound(Gdx.files.internal("sound/kamaz_destroy.mp3"));
    	music = Gdx.audio.newMusic(Gdx.files.internal("sound/bgm.mp3"));
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
        TextureRegion[][] framesTntVehicle = TextureRegion.split(Assets.tntVehicleTexture,
                Assets.tntVehicleTexture.getWidth() / 3,
                Assets.tntVehicleTexture.getHeight());
        TextureRegion[][] framesAidVehicle = TextureRegion.split(Assets.aidVehicleTexture,
                Assets.aidVehicleTexture.getWidth() / 3,
                Assets.aidVehicleTexture.getHeight());
        TextureRegion[][] framesExplosion = TextureRegion.split(Assets.explosionSheet,
                Assets.explosionSheet.getWidth() / 13,
                Assets.explosionSheet.getHeight());

        leftUnbrocken = framesEnemy[6][0];
        rightUnbrocken = framesEnemy[6][1];
        leftTopUnbrocken = framesEnemy[4][0];
        rightToppUnbrocken = framesEnemy[4][1];
        leftBrocken = framesEnemy[5][0];
        rightBrocken = framesEnemy[5][1];

        mdIdle = new Animation<>(0, framesEnemy[3][3]);

        mdShoot = new Animation<>(Constants.ANIMATION_SPEED, new TextureRegion[]{
                framesEnemy[3][3], framesEnemy[4][3]
        });
        mdShoot.setPlayMode(Animation.PlayMode.LOOP);

        explosionAnimation = new Animation<>(Constants.ANIMATION_SPEED, framesExplosion[0]);
        explosionAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        presidentRunRight = new Animation<>(Constants.ANIMATION_SPEED, new TextureRegion[] {
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

        presidentRunLeft = new Animation<>(Constants.ANIMATION_SPEED, new TextureRegion[] {
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

        presidentIdle =  new Animation<>(Constants.ANIMATION_SPEED, new TextureRegion[]{
                presidentFrames[0][1]
        });
        presidentIdle.setPlayMode(Animation.PlayMode.NORMAL);

        presidentAimDown = new Animation<>(Constants.ANIMATION_SPEED, new TextureRegion[]{
                presidentFrames[0][8]
        });
        presidentAimDown.setPlayMode(Animation.PlayMode.NORMAL);

        presidentAimMiddle = new Animation<>(Constants.ANIMATION_SPEED, new TextureRegion[]{
                presidentFrames[0][9]
        });
        presidentAimMiddle.setPlayMode(Animation.PlayMode.NORMAL);

        presidentAimUp = new Animation<>(Constants.ANIMATION_SPEED, new TextureRegion[]{
                presidentFrames[0][10]
        });
        presidentAimUp.setPlayMode(Animation.PlayMode.NORMAL);

        oilFactoryAppearance = new Animation<>(Constants.ANIMATION_SPEED, new TextureRegion[] {
                framesOilFactory[0][0],
                framesOilFactory[0][1],
                framesOilFactory[0][2],
                framesOilFactory[0][3]
        });
        oilFactoryAppearance.setPlayMode(Animation.PlayMode.NORMAL);

        oilFactoryDisappearance = new Animation<>(Constants.ANIMATION_SPEED, new TextureRegion[] {
                framesOilFactory[0][3],
                framesOilFactory[0][2],
                framesOilFactory[0][1],
                framesOilFactory[0][0]
        });
        oilFactoryDisappearance.setPlayMode(Animation.PlayMode.NORMAL);

        oilFieldAnimation = new Animation<>(Constants.ANIMATION_SPEED_SLOW, new TextureRegion[]{
                framesOilField[0][0],
                framesOilField[0][1]
        });
        oilFieldAnimation.setPlayMode(Animation.PlayMode.LOOP);

        tntVehicleRiding = new Animation<>(Constants.ANIMATION_SPEED, new TextureRegion[] {
                framesTntVehicle[0][0], framesTntVehicle[0][1]
        });
        tntVehicleRiding.setPlayMode(Animation.PlayMode.LOOP);
        tntVehicleExploded = framesTntVehicle[0][2];
        tntVehicleExploded.flip(true, true);

        framesAidVehicle[0][0].flip(true, false);
        framesAidVehicle[0][1].flip(true, false);
        framesAidVehicle[0][2].flip(true, false);

        aidVehicleRiding = new Animation<>(Constants.ANIMATION_SPEED, new TextureRegion[] {
                framesAidVehicle[0][0], framesAidVehicle[0][1]
        });
        aidVehicleRiding.setPlayMode(Animation.PlayMode.LOOP);

        aidVehicleExploded = framesAidVehicle[0][2];
        aidVehicleExploded.flip(false, true);
    }
}
