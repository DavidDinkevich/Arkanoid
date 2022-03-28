// ID: 584698174

//package game;

import game.Game;

/**
 * Main class of the game.
 * @author David Dinkevich
 */
public class Main {

    /**
     * Opens a new window and begins a new game.
     * @param args ignored
     */
    public static void main(String[] args) {
        // Window size 800x600
        Game game = new Game("Arkanoid", 800, 600);
        game.initialize();
        game.run();
    }

}
