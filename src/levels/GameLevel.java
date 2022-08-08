// ID: 584698174

package levels;

import animations.Animation;
import animations.PauseScreen;
import animations.KeypressStoppableAnimation;
import animations.AnimationRunner;
import animations.CountdownAnimation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import core.GameEnvironment;
import core.ScoreIndicator;
import core.Sprite;
import core.Counter;
import core.Collidable;
import core.Paddle;
import core.Block;
import core.Ball;

import core.SpriteCollection;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import communication.BallRemover;
import communication.BlockRemover;
import communication.ScoreTrackingListener;

import java.awt.Color;
import java.util.List;

/**
 * In charge of loading and running levels. Manages the basic behavior
 * of running a level.
 * @author David Dinkevich
 */
public class GameLevel implements Animation {
    /** Stores the sprites of the game. */
    private SpriteCollection sprites;
    /** In charge of collisions. */
    private GameEnvironment env;
    /** The width of the screen. */
    private int width;
    /** The height of the screen. */
    private int height;
    /** Controls whether or not the animation is running. */
    private boolean running;
    /** Counter for number of blocks. */
    private Counter numBlocks;
    /** Counter for number of balls. */
    private Counter numBalls;
    /** Counter for score. */
    private Counter score;
    /** The current level being run. */
    private LevelInformation currLevel;
    /** Runs the game loop. */
    private AnimationRunner animationRunner;
    /** The keyboard sensor. */
    private KeyboardSensor keyboard;

    /**
     * Instantiates a new Game object.
     * @param width the width of the game window
     * @param height the height of the game window
     * @param currLevel the LevelInformation object detailing the
     *                  current level
     * @param animationRunner the AnimationRunner that runs the game loop
     * @param keyboard the keyboard sensor
     * @param score the score counter
     */
    public GameLevel(int width, int height, LevelInformation currLevel,
                     AnimationRunner animationRunner, KeyboardSensor keyboard,
                     Counter score) {
        this.width = width;
        this.height = height;
        this.currLevel = currLevel;

        // Counters
        numBlocks = new Counter(0);
        numBalls = new Counter(0);
        this.score = score;

        // Initialize GameEnvironment and SpriteCollection
        sprites = new SpriteCollection();
        env = new GameEnvironment();

        this.keyboard = keyboard;
        this.animationRunner = animationRunner;
    }

    /**
     * Adds a collidable to the GameEnvironment.
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        env.addCollidable(c);
    }

    /**
     * Adds a Sprite to the SpriteCollection.
     * @param s the Sprite to add
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Removes a collidable from the GameEnvironment.
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        env.removeCollidable(c);
    }

    /**
     * Removes a Sprite from the SpriteCollection.
     * @param s the Sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Check for pause
        if (keyboard.isPressed("p")) {
            animationRunner.run(new KeypressStoppableAnimation(
                    keyboard, KeyboardSensor.SPACE_KEY, new PauseScreen()
            ));
        }

        // Draw the sprites
        sprites.drawAllOn(d);
        sprites.notifyAllTimePassed();
        // Update running flag
        running = numBlocks.getValue() > 0 && numBalls.getValue() > 0;
    }

    @Override
    public boolean shouldStop() {
        return !running;
    }

    /**
     * Runs a countdown animation, and then the current level. Ends when
     * the player wins or loses.
     */
    public void playOneTurn() {
        animationRunner.run(new CountdownAnimation(2, 3, sprites));
        running = true;
        animationRunner.run(this);
    }

    /**
     * Initialize a new game: create the Blocks, Ball, and Paddle
     * and add them to the game.
     */
    public void initialize() {
        // ADD LEVEL BACKGROUND
        addSprite(currLevel.getBackground());

        // CREATE BLOCKS
        List<Block> blocks = currLevel.blocks();
        numBlocks.increase(blocks.size());

        for (Block block : blocks) {
            block.addHitListener(new BlockRemover(this, numBlocks));
            block.addHitListener(new ScoreTrackingListener(score));
            block.addToGame(this);
        }

        // CREATE WALLS
        double borderWidth = 30;
        Color wallColor = new Color(0.25f, 0, 0.25f);
        createBorderWalls(borderWidth, wallColor);

        // CREATE PADDLE
        Color paddleColor = new Color(0.9f, 0.11f, 0.9f);
        double paddleWidth = currLevel.paddleWidth();
        double paddleHeight = 8;
        double paddleSpeed = currLevel.paddleSpeed();
        // Prevent paddle from passing the walls--define max range it can move
        double maxRange = width / 2 - borderWidth;
        // Raise paddle a bit off of the ground
        double paddleY = height - 1.1 * paddleHeight;
        // Paddle starts in the middle of the screen
        double paddleX = width / 2 - paddleWidth / 2;
        Point paddlePos = new Point(paddleX, paddleY);
        Rectangle paddleShape = new Rectangle(paddlePos, paddleWidth, paddleHeight);
        Paddle paddle = new Paddle(keyboard, paddleShape, paddleColor,
                paddleSpeed, maxRange);
        paddle.addToGame(this);

        // CREATE BALLS
        int ballRadius = 5;
        numBalls.increase(currLevel.numberOfBalls());
        List<Velocity> ballVelocities = currLevel.initialBallVelocities();

        for (int i = 0; i < numBalls.getValue(); i++) {
            // Arrange balls at center above the paddle
            double x = width / 2 - ballRadius / 2;
            Ball ball = new Ball(env, (int) x, (int) paddleY - 10,
                    ballRadius, new Color(1, 0.5f, 0.6f));
            ball.setVelocity(ballVelocities.get(i));
            ball.addToGame(this);
        }

        // SCORE INDICATOR
        int labelsY = (int) (borderWidth * 0.8);
        int scoreIndicatorSize = 20;
        int fontSize = (int) (scoreIndicatorSize * 0.8);
        ScoreIndicator scoreIndicator = new ScoreIndicator(
                width / 2, labelsY, fontSize, score);
        scoreIndicator.addToGame(this);

        // LEVEL NAME LABEL
        addSprite(new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(Color.WHITE);
                d.drawText((int) (width * 0.7), labelsY,
                        "Level: " + currLevel.levelName(), fontSize);
            }
            // Do nothing
            @Override
            public void timePassed() {
            }
            @Override
            public void addToGame(GameLevel g) {
                g.addSprite(this);
            }
        });

        // CREATE DEATH REGION BLOCK
        Block deathRegionBlock = new Block(
                new Rectangle(
                        0,
                        height + 10, // A little below the screen (10 px)
                        width,
                        borderWidth
                ),
                wallColor, wallColor
        );
        // Add a ball remover listener to the death block
        deathRegionBlock.addHitListener(new BallRemover(this, numBalls));
        deathRegionBlock.addToGame(this);
    }

    /**
     * Create the blocks (walls) that prevent the balls from leaving the screen.
     * @param borderWidth the thickness of the walls
     * @param wallColor the color of the borderWalls
     */
    private void createBorderWalls(double borderWidth, Color wallColor) {
        // CREATE WALLS
        Block leftWall = new Block(new Rectangle(
                0, 0, borderWidth, height), wallColor, wallColor);
        Block rightWall = new Block(new Rectangle(
                width - borderWidth, 0,
                borderWidth, height), wallColor, wallColor);
        Block topWall = new Block(new Rectangle(
                borderWidth, 0, width - 2 * borderWidth,
                borderWidth), wallColor, wallColor);
        // ADD WALLS TO GAME
        leftWall.addToGame(this);
        topWall.addToGame(this);
        rightWall.addToGame(this);
    }

    /**
     * Get a Counter that contains the number of Blocks in this Game.
     * @return the number of blocks in this Game
     */
    public Counter getNumBlocks() {
        return numBlocks;
    }

    /**
     * Get a Counter that contains the number of Balls in this Game.
     * @return the number of balls in this Game
     */
    public Counter getNumBalls() {
        return numBalls;
    }
}
