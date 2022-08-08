// ID: 584698174

import core.GameFlow;

import java.io.File;

/**
 * Main class of the game.
 * @author David Dinkevich
 */
public class Ass7Game {

    /**
     * Opens a new window and begins a new game.
     * @param args the level file to run (if empty runs a default level file)
     */
    public static void main(String[] args) {
        // Screen is 800x600, frame rate = 60
        int screenWidth = 800;
        int screenHeight = 600;
        int frameRate = 60;
        // Create the GameFlow
        GameFlow gameFlow = new GameFlow(screenWidth, screenHeight, frameRate);
        gameFlow.createWindow();

        File levelsFile;
        // If a file was given in the args and it exists, run it
        if (args.length > 0 && new File(args[0]).exists()) {
            levelsFile = new File(args[0]);
        } else {
            // Run default file
            levelsFile = new File("resources/definitions/minecraft_level_definitions.txt");
        }
        gameFlow.runLevelsFile(levelsFile);
    }


}
