package com.catinthedark.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.assets.Assets;

/**
 * Created by Ilya on 28.04.2014.
 */
public class Hint extends Entity {
    public static final float WIDTH = 10f;
    public static final float HEIGHT = 4f;
    private final Texture texture;

    public enum Tip {
        OIL_HINT
    }

    public Hint(float x, float y, Tip t) {
        super(x, y, WIDTH, HEIGHT);
        switch (t) {
            case OIL_HINT:
                texture = Assets.oilHintTexture;
                break;
            default:
               texture = null;
        }
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        batch.draw(texture, this.x, this.y, this.width, this.height);
    }
}
