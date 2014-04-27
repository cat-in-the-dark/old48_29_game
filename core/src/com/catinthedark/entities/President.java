package com.catinthedark.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.Constants;
import com.catinthedark.GameScore;
import com.catinthedark.assets.Assets;
import com.catinthedark.entities.Entity.Direction;
import com.catinthedark.entities.Entity.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ilya on 26.04.2014.
 */
public class President {
	public static final int WIDTH = 6;
	public static final int HEIGHT = 9;

	private final Vector2 minAcceleration = new Vector2(0f, 0f);
	private final Vector2 maxAcceleration = Constants.PRESIDENT_SPEED;
	private Vector2 acceleration = new Vector2(minAcceleration.x,
			minAcceleration.y);
	public State state;
	public Direction direction;
	private List<Rectangle> bounds;

	private float x;
	private float y;

	private double shutDelay = 0.5;
	private double lastShutTime = 0;
	private float stateTime = 0;

	private final Random rand = new Random(System.nanoTime());

	public President(float x, float y) {
		this.direction = Direction.RIGHT;
		this.state = State.IDLE;

		this.x = x;
		this.y = y;

		bounds = new ArrayList<Rectangle>(3);
		bounds.add(new Rectangle(x, y, (float) WIDTH * 3 / 4, HEIGHT * 0.32f));
		bounds.add(new Rectangle(x, y + HEIGHT * 0.32f, (float) WIDTH * 0.58f,
				HEIGHT * 0.19f));
		bounds.add(new Rectangle(x, y + HEIGHT * (0.32f + 0.19f),
				(float) WIDTH * 0.9f, HEIGHT * 0.22f));
		bounds.add(new Rectangle(x, y + HEIGHT * (0.32f + 0.19f + 0.22f),
				(float) WIDTH * 0.54f, HEIGHT * 0.2f));
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void render(float delta, SpriteBatch batch) {
		stateTime += delta;
		lastShutTime += delta;
		tryMove();
		batch.draw(playAnimation(stateTime), this.x, this.y - 0.5f, WIDTH, HEIGHT);
		// batch.end();
		//
		// ShapeRenderer shapeRenderer = new ShapeRenderer();
		// shapeRenderer.begin(ShapeType.Line);
		// shapeRenderer.setColor(Color.RED);
		// for (Rectangle bound : bounds)
		// shapeRenderer.rect(bound.x * Constants.UNIT_SIZE, bound.y
		// * Constants.UNIT_SIZE, bound.width * Constants.UNIT_SIZE,
		// bound.height * Constants.UNIT_SIZE);
		// shapeRenderer.end();
		// batch.begin();
	}

	private TextureRegion playAnimation(float stateTime) {
        switch (state) {
            case IDLE:
                return Assets.presidentAimMiddle.getKeyFrame(stateTime);
            case AIM_UP:
                return Assets.presidentAimUp.getKeyFrame(stateTime);
            case AIM_DOWN:
                return Assets.presidentAimDown.getKeyFrame(stateTime);
            case RUN:
                switch (direction) {
                    case RIGHT:
                        return Assets.presidentRunRight.getKeyFrame(stateTime);
                    case LEFT:
                        return Assets.presidentRunLeft.getKeyFrame(stateTime);
                }
        }

		return Assets.presidentAimMiddle.getKeyFrame(stateTime);
	}

	public Rocket shut() {
		if (lastShutTime > shutDelay) {
			lastShutTime = 0;
            switch (state) {
                case AIM_DOWN:
                    return new Rocket(this.x + 3f * WIDTH / 5f , this.y + HEIGHT / 2f + 0.1f, this);
                case AIM_UP:
                    return new Rocket(this.x + 3f * WIDTH / 5f , this.y + HEIGHT / 2f + 1.2f, this);
                default:
                    return new Rocket(this.x + 3f * WIDTH / 5f , this.y + HEIGHT / 2f + 0.8f, this);
            }
		}
		return null;
	}

	public void move(State state, Camera camera) {
		if (state != State.RUN) {
			this.state = state;
			acceleration.x = minAcceleration.x;
		} else {
			this.state = State.RUN;
			switch (direction) {
			case RIGHT:
				acceleration.x = maxAcceleration.x;
				break;
			case LEFT:
				acceleration.x = -maxAcceleration.x;
				break;
			}
		}

		if (!canMove(camera)) {
			acceleration.x = minAcceleration.x;
		}
	}

	private void tryMove() {
		this.x += acceleration.x;
		for (Rectangle bound : bounds)
			bound.x = x;

	}

	public boolean canMove(Camera camera) {
		float nextX = this.x + acceleration.x;
		return !(camera.position.x - WIDTH
				- Constants.maxPresidentDestinationFromBorder < nextX || nextX < camera.position.x
				- camera.viewportWidth / 2f);
	}

	public void doDamage(int amount) {
		if (rand.nextInt() % 2 == 0)
			Assets.oh1Sound.play(0.7f);
		else
			Assets.oh2Sound.play(0.7f);
		
		GameScore.getInstance().setHealth(GameScore.getInstance().getHealth() - amount);

	}

	public List<Rectangle> getBounds() {
		return bounds;
	}

}
