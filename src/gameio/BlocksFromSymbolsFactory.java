// ID: 584698174

package gameio;

import core.Block;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating blocks/spacers based on a level layout described
 * in a Levels Specification File and blocks based on a Blocks Specification File.
 * @author David Dinkevich
 */
public class BlocksFromSymbolsFactory {

   /** Map for storing spacers. */
   private Map<String, Integer> spacerWidths;
   /** Map for storing Block Creators. */
   private Map<String, BlockCreator> blockCreators;

   /**
    * Instantiate a new BlocksFromSymbolsFactory.
    */
   public BlocksFromSymbolsFactory() {
      spacerWidths = new HashMap<>();
      blockCreators = new HashMap<>();
   }

   /**
    * Returns true if "s" is a valid space symbol.
    * @param s the symbol to test
    * @return true if "s" is a valid space symbol
    */
   public boolean isSpaceSymbol(String s) {
      return spacerWidths.containsKey(s);
   }

   /**
    * Returns true if "s" is a valid block symbol.
    * @param s the symbol to test
    * @return true if "s" is a valid block symbol
    */
   public boolean isBlockSymbol(String s) {
      return blockCreators.containsKey(s);
   }

   /**
    * Returns a block according to the definitions associated with the symbol "s".
    * The block will be located at the position (xpos, ypos).
    * @param s the symbol of the block
    * @param xpos the x location
    * @param ypos the y location
    * @return a new block belonging to the symbol s at (xpos, ypos).
    */
   public Block getBlock(String s, int xpos, int ypos) {
      return blockCreators.get(s).create(xpos, ypos);
   }

   /**
    * Returns the width in pixels associated with the given spacer-symbol.
    * @param s the spacer symbol
    * @return the width in pixels associated with the given spacer-symbol.
    */
   public int getSpaceWidth(String s) {
      return spacerWidths.get(s);
   }

   /**
    * Add a new spacer definition.
    * @param key the spacer symbol
    * @param val the space in pixels
    */
   public void addSpacer(String key, int val) {
      spacerWidths.put(key, val);
   }

   /**
    * Add a new block type.
    * @param key the symbol of the block type
    * @param blockCreator a BlockCreator that creates blocks of this type
    */
   public void addBlockType(String key, BlockCreator blockCreator) {
      blockCreators.put(key, blockCreator);
   }
}
