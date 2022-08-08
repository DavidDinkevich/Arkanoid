// ID: 584698174

package core;

import animations.Animation;
import animations.AnimationRunner;
import animations.KeypressStoppableAnimation;
import animations.LoseScreen;
import animations.Menu;
import animations.MenuAnimation;
import animations.WinScreen;

import biuoop.GUI;
import biuoop.KeyboardSensor;

import communication.ShowHiScoresTask;
import communication.Task;

import gameio.FileUtils;
import gameio.LevelSpecificationReader;

import levels.LevelInformation;
import levels.GameLevel;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Controls the flow of the game--creates the window, runs sequences of levels,
 * etc.
 * @author David Dinkevich
 */
public class GameFlow {

    /** The GUI to display the game on. */
    private GUI gui;
    /** Main AnimationRunner that runs the animations. */
    private AnimationRunner animationRunner;
    /** Width of the screen. */
    private int screenWidth;
    /** Height of the screen. */
    private int screenHeight;
    /** Frame rate of the game. */
    private int frameRate;
    /** Counter for score. */
    private Counter score;

    /**
     * Instantiate a new GameFlow object with the given screen dimensions
     * and frame rate.
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     * @param frameRate the frame rate
     */
    public GameFlow(int screenWidth, int screenHeight, int frameRate) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.frameRate = frameRate;
        score = new Counter(0);
    }

    /**
     * Creates a new window.
     */
    public void createWindow() {
        gui = new GUI("Arkanoid", screenWidth, screenHeight);
        animationRunner = new AnimationRunner(gui, frameRate);
    }

    /**
     * Creates a game menu.
     * @param levelsFile the levels file that will be loaded
     * @return the game menu
     */
    private Menu<Task<Void>> createMenu(File levelsFile) {
        Menu<Task<Void>> menu = new MenuAnimation<>(gui.getKeyboardSensor());
        menu.addSelection("s", "New Game", new Task<Void>() {
            @Override
            public Void run() {
                // Run the levels in the file
                runLevels(readLevels(levelsFile));
                // Update the high score
                FileUtils.updateHighScore(score.getValue());
                // Show high score
                new ShowHiScoresTask(animationRunner, gui.getKeyboardSensor()).run();
                return null;
            }
        });
        menu.addSelection("h", "High Score",
                new ShowHiScoresTask(animationRunner, gui.getKeyboardSensor()));
        menu.addSelection("q", "Quit", new Task<Void>() {
            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        });
        return menu;
    }

    /**
     * Loads the levels file at the given path and runs the levels within it.
     * @param levelsFile the file containing information about the levels
     */
    public void runLevelsFile(File levelsFile) {
        // Update the high score--creates a highscores file if none exists
        FileUtils.updateHighScore(0);
        // Create menu
        Menu<Task<Void>> menu = createMenu(levelsFile);

        while (true) {
            animationRunner.run(menu);
            menu.getStatus().run();
        }
    }

    /**
     * Runs the levels contained in the given list in sequential order.
     * @param levels the list of levels to run
     */
    private void runLevels(List<LevelInformation> levels) {
        // Zero the score counter
        score.decrease(score.getValue());
        boolean playerLost = false;

        // For each level
        for (LevelInformation levelInfo : levels) {
            // Create a new GameLevel to run the level
            GameLevel gameLevel = new GameLevel(
                    screenWidth, screenHeight, levelInfo, animationRunner,
                    gui.getKeyboardSensor(), score);
            gameLevel.initialize();

            // While the player has not lost, run the level
            while (gameLevel.getNumBlocks().getValue() > 0
                    && gameLevel.getNumBalls().getValue() > 0) {
                gameLevel.playOneTurn();
            }

            // If no balls, player lost level
            if (gameLevel.getNumBalls().getValue() == 0) {
                playerLost = true;
                break;
            }
            // Extra 100 points for destroying all of the blocks
            score.increase(100);
        }

        // Display win/lose screen depending on whether the player won/lost
        Animation finalAnim = playerLost ? new LoseScreen(score.getValue())
                : new WinScreen(score.getValue());
        // Display
        animationRunner.run(new KeypressStoppableAnimation(
                gui.getKeyboardSensor(),
                KeyboardSensor.SPACE_KEY,
                finalAnim
        ));
    }

    /**
     * Parses the given levels file and extracts the levels described in it.
     * @param levelsFile the file to parse
     * @return a list of the levels described within the file
     */
    private List<LevelInformation> readLevels(File levelsFile) {
        // Return a list of the levels
        try {
            // Create new LevelSpecificationReader
            return new LevelSpecificationReader(screenWidth, screenHeight)
                    .fromReader(new FileReader(levelsFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
