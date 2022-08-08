// ID: 584698174

package gameio;

import core.Block;
import core.ImageBlock;

import geometry.Rectangle;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Contains useful static methods for interacting with files.
 * @author David Dinkevich
 */
public final class FileUtils {

    /**
     * This class is non-instantiable.
     */
    private FileUtils() {
    }

    /**
     * Returns the high score stored within the "highscores.txt" file.
     * @return the high score
     */
    public static int getHighScore() {
        File highScoreFile = new File("highscores.txt");
        int highScore = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(highScoreFile));
            String line = reader.readLine();
            reader.close();
            if (line != null) {
                highScore = Integer.parseInt(line.split(": ")[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScore;
    }

    /**
     * Sets the high score stored within the "highscores.txt" file the the maximum
     * between the value already stored in the file, and the given parameter. If the file
     * does not yet exist, the file will be created.
     * @param currScore the score to update the file with (if it's greater than the
     *                  current score)
     */
    public static void updateHighScore(int currScore) {
        File highScoreFile = new File("highscores.txt");
        try {
            int highScore = 0;
            // If the file already exists, read from it the high score
            if (highScoreFile.exists()) {
                highScore = FileUtils.getHighScore();
            } else { // If the file does not yet exist, create it
                highScoreFile.createNewFile();
            }
            // Write to the file the maximum between currScore and highScore
            int newHighScore = Math.max(currScore, highScore);
            FileWriter writer = new FileWriter(highScoreFile);
            writer.write("The highest score so far is: " + newHighScore);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads an image from the given file.
     * @param file the file from which the image will be loaded
     * @return an Image class representing the image
     */
    public static Image loadImage(String file) {
        Image img = null;
        try {
            img = ImageIO.read(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Accepts a string in the form of "color(...)" or "image(file_name.ext)" and
     * returns a Block in the given color/image respectively, with the given dimensions.
     * @param fillInfo the string containing the fill color/image information
     * @param strokeInfo the string containing the stroke color information
     * @param dims the dimensions of the block
     * @return the block
     */
    public static Block generateDecoratedBlock(String fillInfo, String strokeInfo, Rectangle dims) {
        // Note--the stroke must be a color, can't be an image
        // Use a ColorsParser to get the stroke
        Color strokeCol = new ColorsParser().colorFromString(strokeInfo);
        // For fill, we need to determine if we're given a color or image
        if (fillInfo.contains("image")) {
            // Get "fileName.ext" from "image(fileName.ext)" and extract "fileName.ext"
            String file = fillInfo.split("\\(")[1].split("\\)")[0];
            return new ImageBlock(dims, loadImage(file), strokeCol);
        } else {
            // Use a ColorsParser to get the fill
            Color fill = new ColorsParser().colorFromString(fillInfo);
            return new Block(dims, fill, strokeCol);
        }
    }

}
