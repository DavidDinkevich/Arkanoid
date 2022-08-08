// ID: 584698174

package gameio;

import core.Block;
import core.Sprite;

import geometry.Rectangle;

import levels.LevelBuilder;
import levels.LevelInformation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In charge of reading level specification files, and returns a list of Levels
 * defined in a level specification file.
 * @author David Dinkevich
 */
public class LevelSpecificationReader {

    /** Width of the screen. */
    private int width;
    /** Height of the screen. */
    private int height;

    /**
     * Instantiates a new LevelSpecificationReader.
     * @param width the width of the screen
     * @param height the height of the screen
     */
    public LevelSpecificationReader(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Get a list of levels defined in a level specification file.
     * @param reader a reader that points to a level specification file
     * @return the list of levels defined in the level specification file
     */
    public List<LevelInformation> fromReader(Reader reader) {
        BufferedReader buff = new BufferedReader(reader);
        // List of levels
        List<LevelInformation> levels = new ArrayList<>();
        try {
            String line;
            // Loop through file until we find all of the levels
            while (true) {
                line = buff.readLine();
                // If the line is null, we've gone through the whole file
                if (line == null) {
                    break;
                // Skip to the next occurrence of the line "START_LEVEL"
                } else if (!line.equals("START_LEVEL")) {
                    continue;
                }
                levels.add(parseLevel(buff)); // Add the level
            }
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levels;
    }

    /**
     * Read one START_LEVEL END_LEVEL block and extract the level.
     * @param buff the reader of the file
     * @return the level
     * @throws IOException if there is an exception while reading the file
     */
    private LevelInformation parseLevel(BufferedReader buff) throws IOException {
        // Map to store the data of the level
        Map<String, String> levelBuilderData = new HashMap<>();

        for (String line = buff.readLine(); !line.equals("START_BLOCKS"); line = buff.readLine()) {
            // Ignore empty lines, commented out lines, and lines that don't have
            // a key-value pair
            if (line.startsWith("#") || line.isBlank() || !line.contains(":")) {
                continue;
            }
            // Put the key-value pair in the map
            String[] splitByColons = line.split(":");
            String key = splitByColons[0];
            String value = line.substring(key.length() + 1);
            levelBuilderData.put(key, value);
        }

        // Get background info
        Rectangle dims = new Rectangle(0, 0, width, height);
        String backgroundVal = levelBuilderData.get("background");
        // Stroke is black
        Sprite background = FileUtils.generateDecoratedBlock(backgroundVal, "color(black)", dims);

        // Get block definitions file
        String blockDefinitionsFile = levelBuilderData.get("block_definitions");
        // If we were not given a block definitions file, the level is invalid
        if (blockDefinitionsFile == null) {
            return null;
        }
        // Make sure that num_blocks is given information
        if (!levelBuilderData.containsKey("num_blocks")) {
            throw new RuntimeException("Level information missing");
        }
        // Read the START_BLOCKS END_BLOCKS block and extract the list of blocks
        List<Block> blocks = generateBlocks(blockDefinitionsFile, buff, levelBuilderData);
        return new LevelBuilder(levelBuilderData, background, blocks);
    }

    /**
     * Reads a START_BLOCKS END_BLOCKS block and creates from it a list of blocks.
     * @param blockDefinitionsFile the block definitions file associated with this
     *                             levels specification file
     * @param buff the reader for the file
     * @param levelInfo a map containing the level information
     * @return the list of blocks
     * @throws IOException if an exception is thrown while reading the file
     */
    private List<Block> generateBlocks(String blockDefinitionsFile, BufferedReader buff,
            Map<String, String> levelInfo) throws IOException {
        // Generate a BlockFromSymbolsFactory from the level info file
        BlocksFromSymbolsFactory factory = BlocksDefinitionReader
                .fromReader(new FileReader(new File(blockDefinitionsFile)));

        List<Block> blocks = new ArrayList<>();
        // Get the initial y location of the blocks
        int y = Integer.parseInt(levelInfo.get("blocks_start_y"));
        for (String line = buff.readLine(); !line.equals("END_BLOCKS"); line = buff.readLine()) {
            // Get the initial x location of the blocks
            int x = Integer.parseInt(levelInfo.get("blocks_start_x"));
            line = line.trim(); // Get rid of beginning/trailing whitespace
            // If this entire line is not just a single space symbol
            if (!line.isEmpty() && !factory.isSpaceSymbol(line)) {
                // Read the line character by character
                for (int i = 0; i < line.length(); i++) {
                    String ch = line.substring(i, i + 1);
                    // If the char is a spacer than add a space
                    if (factory.isSpaceSymbol(ch)) {
                        x += factory.getSpaceWidth(ch);
                    // If it's a block then create/add the block
                    } else if (factory.isBlockSymbol(ch)) {
                        Block block = factory.getBlock(ch, x, y);
                        blocks.add(block);
                        x += block.getCollisionRectangle().getWidth();
                    // Symbol is undefined
                    } else {
                        throw new RuntimeException("Illegal symbol: " + ch);
                    }
                }
            }
            // Move down one row
            y += Integer.parseInt(levelInfo.get("row_height"));
        }
        return blocks;
    }
}
