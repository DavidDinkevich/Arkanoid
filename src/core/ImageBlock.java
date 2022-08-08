// ID: 584698174

package core;

import biuoop.DrawSurface;

import geometry.Rectangle;

import java.awt.Color;
import java.awt.Image;

/**
 * A block on which an image is rendered.
 * @author David Dinkevich
 */
public class ImageBlock extends Block {

    /** The image to render. */
    private Image image;

    /**
     * Instantiates a new ImageBlock with the given shape and image.
     * @param shape the geometric shape of the Block
     * @param image the image of the block
     * @param stroke the stroke color of the block
     */
    public ImageBlock(Rectangle shape, Image image, Color stroke) {
        super(shape, null, stroke);
        this.image = image;
    }

    @Override
    public void drawOn(DrawSurface d) {
        Rectangle r = getCollisionRectangle();
        d.drawImage((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(), image);
        // Draw the border of the block
        d.setColor(getStroke());
        d.drawRectangle((int) r.getUpperLeft().getX(), (int) r.getUpperRight().getY(),
                (int) r.getWidth(), (int) r.getHeight());

    }
}
