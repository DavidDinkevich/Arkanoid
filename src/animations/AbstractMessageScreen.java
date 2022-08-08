// ID: 584698174

package animations;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * An animation that displays a message in the center of the screen
 * and draws a grid pattern around the borders of the screen.
 * @author David Dinkevich
 */
public abstract class AbstractMessageScreen implements Animation {
    /** The background color. */
    private Color backgroundColor;
    /** The color of the checkerboard pattern. */
    private Color checkerboardColor;

    /**
     * Instantiates a new AbstractMessageScreen with the given background color
     * and checkerboard pattern color.
     * @param backgroundColor the background color of the screen
     * @param checkerboardColor the color of the checkerboard pattern around
     *                          the borders of the screen
     */
    public AbstractMessageScreen(Color backgroundColor, Color checkerboardColor) {
        this.backgroundColor = backgroundColor;
        this.checkerboardColor = checkerboardColor;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw background
        d.setColor(backgroundColor);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        // Draw checkerboard border
        d.setColor(checkerboardColor);
        int width = 60;
        drawCheckerboard(d, 0, 0, d.getWidth(), width, 10);
        drawCheckerboard(d, 0, d.getHeight() - width, d.getWidth(), d.getHeight(), 10);
        drawCheckerboard(d, 0, width, width, d.getHeight() - width, 10);
        drawCheckerboard(d, d.getWidth() - width, width, d.getWidth(), d.getHeight() - width, 10);
    }

    /**
     * Draws a checkerboard grid within the given boundaries.
     * @param d the DrawSurface to draw on
     * @param xStart the top left x-coordinate of the grid
     * @param yStart the top left y-coordinate of the grid
     * @param xEnd the bottom right x-coordinate of the grid
     * @param yEnd the bottom right y-coordinate of the grid
     * @param blockSize the size of each square in the checkerboard
     */
    private void drawCheckerboard(DrawSurface d, int xStart, int yStart, int xEnd, int yEnd,
                                  int blockSize) {
        for (int x = xStart; x < xEnd; x += blockSize) {
            for (int y = yStart; y < yEnd; y += blockSize) {
                if ((x + y) % (blockSize * 2) == 0) {
                    d.fillRectangle(x, y, blockSize, blockSize);
                }
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return false;
    }


}
