// ID: 584698174

package gameio;

import core.Block;

import geometry.Rectangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * In charge of reading block definition files, and returns a BlocksFromSymbolsFactory
 * for any given block definition file.
 * @author David Dinkevich
 */
public class BlocksDefinitionReader {

    /** The attributes of a block that are customized in a block definitions file. */
    private static final String[] BLOCK_ATTRIBUTES = {
            "width", "height", "fill", "stroke"
    };

    /**
     * Accepts a reader to a block definition file, parses the file, and returns a
     * BlocksFromSymbolsFactory based on the file.
     * @param reader a reader pointing to the file
     * @return a BlocksFromSymbolsFactory based on the given block definitions file
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        // Create a buffered reader from the given reader
        BufferedReader buff = new BufferedReader(reader);
        // The factory that we will tailor to this file
        BlocksFromSymbolsFactory factory = new BlocksFromSymbolsFactory();
        // A map containing the default block attributes described in the file:
        // "width", "height", "fill", "stroke"
        Map<String, String> defBlockAttributes = new HashMap<>();
        try {
            for (String line = buff.readLine(); line != null; line = buff.readLine()) {
                // The default block attributes section
                if (line.startsWith("default")) {
                    // Remove the word "default " and split by spaces
                    String[] attributePairs = line.trim().substring("default ".length()).split(" ");
                    // Fill in the default values
                    for (String pair : attributePairs) {
                        String[] components = pair.split(":");
                        // Replace the null values with the given default values
                        defBlockAttributes.put(components[0], components[1]);
                    }
                // The bdef section
                } else if (line.startsWith("bdef")) {
                    // Create a block creator matching this specific symbol and add it to
                    // the factory
                    BlockCreator creator = makeBlockCreatorFromLine(line, defBlockAttributes);
                    factory.addBlockType(getValue(line, "symbol"), creator);
                // Add the spacer matching the given symbol
                } else if (line.startsWith("sdef")) {
                    factory.addSpacer(
                            // Add the spacer
                            getValue(line, "symbol"),
                            // Add the width attribute
                            Integer.parseInt(getValue(line, "width"))
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return factory;
    }

    /**
     * Creates a BlockCreator from the attributes described in the parameter "line",
     * and the map "defAttributes" (the default attributes of a block in this file).
     * @param line the line containing the specific attributes of this block type
     * @param defAttributes contains the default attributes of blocks
     * @return a BlockCreator based on the given information
     */
    private static BlockCreator makeBlockCreatorFromLine(String line,
                                                  Map<String, String> defAttributes) {
        BlockCreator creator = new BlockCreator() {
            @Override
            public Block create(int xpos, int ypos) {
                // Create a map storing the block attributes. It is initially filled with
                // the default attributes of all blocks.
                Map<String, String> blockAttribs = new HashMap<>(defAttributes);

                // For each block attribute, check if the line contains information about
                // the current attribute.If it does, put it in the map. If it doesn't,
                // we'll stick with the default value.
                for (int i = 0; i < BLOCK_ATTRIBUTES.length; i++) {
                    // Find the attribute in the line
                    String findAttributeInLine = getValue(line, BLOCK_ATTRIBUTES[i]);
                    // If the attribute is specified in the line, put it in the map
                    if (findAttributeInLine != null) {
                        blockAttribs.put(BLOCK_ATTRIBUTES[i], findAttributeInLine);
                    }
                }
                // Create the shape of the block
                Rectangle rect = new Rectangle(
                        xpos, ypos,
                        Integer.parseInt(blockAttribs.get("width")),
                        Integer.parseInt(blockAttribs.get("height"))
                );
                // Create the block. (Color/image is specified by the "fill" attribute.)
                String fill = blockAttribs.get("fill");
                String stroke = blockAttribs.get("stroke");
                return FileUtils.generateDecoratedBlock(fill, stroke, rect);
            }
        };
        return creator;
    }

    /**
     * Given a string of key-value pairs, this returns the value of a given key.
     * For example: getValue("a:b c:d e:f", "c") returns "d".
     * @param line the string of key-value pairs
     * @param key the specific key whose value will be retrieved
     * @return the value of the given key, or null if the given key is not within
     * the given string
     */
    private static String getValue(String line, String key) {
        int index = line.indexOf(key + ":");
        return index < 0 ? null : line.substring(index + key.length() + 1).split(" |\n")[0];
    }

}
