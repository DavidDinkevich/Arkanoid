// ID: 584698174

package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;

import java.awt.Color;

/**
 * Contains all of the sprites, collidables involved in a basic game,
 * and is in charge of creating the window and the animation loop.
 * @author David Dinkevich
 */
public class Game {
    /** Stores the sprites of the game. */
    private SpriteCollection sprites;
    /** In charge of collisions. */
    private GameEnvironment env;
    /** The width of the screen. */
    private int width;
    /** The height of the screen. */
    private int height;
    /** The title of the game window. */
    private String title;
    /** The window. */
    private GUI gui;
    /** The score indicator. */
    private ScoreIndicator scoreIndicator;
    /** Counter for number of blocks. */
    private Counter numBlocks;
    /** Counter for number of balls. */
    private Counter numBalls;
    /** Counter for score. */
    private Counter score;
    /** Height of the score indicator. */
    private int scoreIndicatorSize;

    /**
     * Instantiates a new Game object.
     * @param title the title of the game window
     * @param width the width of the game window
     * @param height the height of the game window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        numBlocks = new Counter(0);
        numBalls = new Counter(0);
        score = new Counter(0);

        // Initialize GameEnvironment and SpriteCollection
        sprites = new SpriteCollection();
        env = new GameEnvironment();

        // CREATE WINDOW
        gui = new GUI(title, width, height);
    }

    /**
     * Instantiates a new Game object with title "Game",
     * and screen dimensions 800x600.
     */
    public Game() {
        this("Game", 800, 600);
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

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        Color background = new Color(0.2f, 0, 0.4f);
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        // If no more blocks or balls remain, end the game
        while (numBlocks.getValue() > 0 && numBalls.getValue() > 0) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            // Draw background
            d.setColor(background);
            d.fillRectangle(0, scoreIndicatorSize, width, height);
            // Draw the sprites
            sprites.drawAllOn(d);
            gui.show(d);
            sprites.notifyAllTimePassed();

            // Timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }

        // Extra 100 points for destroying all of the blocks
        if (numBlocks.getValue() == 0) {
            score.increase(100);
        }
        gui.close(); // Close window
    }

    /**
     * Initialize a new game: create the Blocks, Ball, and Paddle
     * and add them to the game.
     */
    public void initialize() {
        // SCORE INDICATOR
        scoreIndicatorSize = 20;
        int fontSize = (int) (scoreIndicatorSize * 0.8);
        scoreIndicator = new ScoreIndicator(width / 2, scoreIndicatorSize, fontSize, score);
        scoreIndicator.addToGame(this);

        // CREATE BALLS
        int ballRadius = 5;
        numBalls.increase(3); // 3 balls
        Color[] ballCols = {
                new Color(0.6f, 0.5f, 1), new Color(1, 0.5f, 0.6f), new Color(1, 0.3f, 0.3f)
        };

        for (int i = 0; i < numBalls.getValue(); i++) {
            double ballSpacing = 50;
            // Arrange blocks in row
            double x = width / 2 - (numBalls.getValue() / 2) * 50 + i * ballSpacing;
            Ball ball = new Ball(env, (int) x, height - 300, ballRadius, ballCols[i]);
            // Generate random angle between -45 and 45 degrees
            double angleRange = 90;
            double angle = Math.random() * angleRange - angleRange / 2;
            // Speed of minimum 4, maximum 7
            double speed = 4 + 3 * Math.random();
            ball.setVelocity(Velocity.fromAngleAndSpeed(angle, speed));
            ball.addToGame(this);
        }

        // CREATE WALLS
        double borderWidth = 30;
        Color wallColor = new Color(0.25f, 0, 0.25f);
        createBorderWalls(borderWidth, wallColor);

        // CREATE DEATH REGION BLOCK
        Block deathRegionBlock = new Block(
                new Rectangle(
                        borderWidth, height + ballRadius,
                        width - 2 * borderWidth, borderWidth
                ),
                wallColor
        );
        // Add a ball remover listener to the death block
        deathRegionBlock.addHitListener(new BallRemover(this, numBalls));
        deathRegionBlock.addToGame(this);

        // CREATE BLOCKS
        int blockWidth = 50;
        int blockHeight = 20;
        int firstRowLength = 12;
        int numRows = 6;
        Color[] rowColors = new Color[] {
                new Color(1f, 0f, 0.5f), new Color(1f, 0.4f, 0.4f),
                new Color(1f, 0.6f, 0.2f), new Color(1f, 1f, 0f),
                new Color(0f, 1f, 0f), new Color(0.2f, 0.2f, 1f)
        };
        createBlocks(blockWidth, blockHeight, firstRowLength, numRows, borderWidth, rowColors);

        // CREATE PADDLE
        Color paddleColor = new Color(0.9f, 0.11f, 0.9f);
        double paddleWidth = 100;
        double paddleHeight = 8;
        double paddleSpeed = 7;
        // Prevent paddle from passing the walls--define max range it can move
        double maxRange = width / 2 - borderWidth;
        // Raise paddle a bit off of the ground
        double paddleY = height - 1.1 * paddleHeight;
        // Paddle starts in the middle of the screen
        double paddleX = width / 2 - paddleWidth / 2;
        Point paddlePos = new Point(paddleX, paddleY);
        Rectangle paddleShape = new Rectangle(paddlePos, paddleWidth, paddleHeight);
        Paddle paddle = new Paddle(gui.getKeyboardSensor(), paddleShape, paddleColor,
                paddleSpeed, maxRange);
        paddle.addToGame(this);
    }

    /**
     * Create the blocks (walls) that prevent the balls from leaving the screen.
     * @param borderWidth the thickness of the walls
     * @param wallColor the color of the borderWalls
     */
    private void createBorderWalls(double borderWidth, Color wallColor) {
        // CREATE WALLS
        Block leftWall = new Block(new Rectangle(
                0, scoreIndicatorSize, borderWidth, height), wallColor);
        Block rightWall = new Block(new Rectangle(
                width - borderWidth, scoreIndicatorSize,
                borderWidth, height), wallColor);
        Block topWall = new Block(new Rectangle(
                borderWidth, scoreIndicatorSize, width - 2 * borderWidth,
                borderWidth), wallColor);
        // ADD WALLS TO GAME
        leftWall.addToGame(this);
        topWall.addToGame(this);
        rightWall.addToGame(this);
    }

    /**
     * Create and lay out the Blocks.
     * @param blockWidth the width of each block
     * @param blockHeight the height of each block
     * @param firstRowLength the length of the first row of blocks
     * @param numRows the total number of rows of blocks
     * @param borderWidth the width of the border blocks around the screen
     * @param rowColors an array of colors, each index representing the color of a row
     */
    private void createBlocks(double blockWidth, double blockHeight, int firstRowLength,
                              int numRows, double borderWidth, Color[] rowColors) {
        for (int j = 0; j < numRows; j++) {
            // y location of blocks for current row
            double y = height / 5 + blockHeight * j;
            for (int i = 0; i < firstRowLength; i++) {
                // x location of blocks for current column
                double x = width - borderWidth - blockWidth * (i + 1);
                // Instantiate the block
                Block block = new Block(new Rectangle(x, y, blockWidth, blockHeight),
                        rowColors[j]);
                numBlocks.increase(1);
                block.addHitListener(new BlockRemover(this, numBlocks));
                block.addHitListener(new ScoreTrackingListener(score));
                block.addToGame(this);
            }
            --firstRowLength; // Each row is shorter than the previous
        }
    }

    /**
     * Get the GameEnvironment of this Game.
     * @return the GameEnvironment of this Game
     */
    public GameEnvironment getGameEnvironment() {
        return env;
    }

    /**
     * Get a Counter that contains the number of Blocks in this Game.
     * @return the number of blocks in this Game
     */
    public Counter getNumBlocks() {
        return numBlocks;
    }

}
