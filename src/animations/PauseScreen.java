// ID: 584698174

package animations;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * An animation that is displayed when the user pauses the game.
 * @author David Dinkevich
 */
public class PauseScreen extends AbstractMessageScreen {

    /**
     * Instantiates a new PauseScreen.
     */
    public PauseScreen() {
        // (backgroundColor, checkerboardColor)
        super(new Color(0x77FF03), new Color(0x15EA13));
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        super.doOneFrame(d);
        d.setColor(new Color(0x0E8E0D));
        d.drawText(d.getWidth() / 2 - 50, d.getHeight() / 2, "Paused", 32);
        d.drawText(d.getWidth() / 2 - 80, d.getHeight() / 2 + 30,
                "(press space to continue)", 15);

        // Draw 3 dots
        int circleRadius = 5;
        for (int i = 0; i < 3; i++) {
            int x = d.getWidth() / 2 - circleRadius * 3;
            d.fillCircle(x + i * circleRadius * 3, d.getHeight() / 2 + 60, circleRadius);
        }
    }

}
