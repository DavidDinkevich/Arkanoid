// ID: 584698174

package animations;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * An animation that is displayed when the user wins the game.
 * @author David Dinkevich
 */
public class WinScreen extends AbstractMessageScreen {

    /** The score to display. */
    private int score;

    /**
     * Instantiates a new WinScreen with the given score to display.
     * @param score the score to display
     */
    public WinScreen(int score) {
        // (backgroundColor, checkerboardColor)
        super(new Color(0xEAA5FF), new Color(0xE590FF));
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        super.doOneFrame(d);
        d.setColor(new Color(0xC10AFA));
        d.drawText(d.getWidth() / 2 - 70, d.getHeight() / 2 - 40, "You Win!", 32);
        d.drawText(d.getWidth() / 2 - 60, d.getHeight() / 2, "your score is: " + score, 15);
        d.drawText(d.getWidth() / 2 - 85, d.getHeight() / 2 + 30, "(press space to continue)", 15);

        // Draw smiley face
        int ballRadius = 6;
        int dist = 70;
        int centerX = d.getWidth() / 2;
        int centerY = (int) (d.getHeight() * 0.7);
        int numBalls = 60;
        for (int i = 0, angle = 180; i < numBalls + 1; i++, angle -= 180 / numBalls) {
            int x = centerX + (int) (dist * Math.cos(Math.toRadians(angle)));
            int y = centerY + (int) (dist * Math.sin(Math.toRadians(angle)));
            d.fillCircle(x, y, ballRadius);
        }
        // Draw eyes
        d.fillCircle(centerX - dist / 2, centerY - dist / 2, ballRadius);
        d.fillCircle(centerX + dist / 2, centerY - dist / 2, ballRadius);
    }

}
