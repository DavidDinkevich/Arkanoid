// ID: 584698174

package animations;

import biuoop.DrawSurface;
import gameio.FileUtils;

import java.awt.Color;

/**
 * An animation that displays the high score.
 * @author David Dinkevich
 */
public class HighScoreScreen extends AbstractMessageScreen {

    /** The score to display. */
    private int highScore;

    /**
     * Instantiates a new HighScoreScreen.
     */
    public HighScoreScreen() {
        // (backgroundColor, checkerboardColor)
        super(new Color(0xFFC250), new Color(0xFFB324));
        highScore = FileUtils.getHighScore();
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        super.doOneFrame(d);
        // SHADOW
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 - 104, d.getHeight() / 2 - 49, "High Score", 50);
        d.drawText(d.getWidth() / 2 - 11, d.getHeight() / 2 + 41, "" + highScore, 30);
//        d.drawText(d.getWidth() / 2 - 75, d.getHeight() / 2 + 181,
//                "(press space to continue)", 15);
        // ACTUAL TEXT
        d.setColor(new Color(0xFF8D00));
        d.drawText(d.getWidth() / 2 - 105, d.getHeight() / 2 - 50, "High Score", 50);
        d.drawText(d.getWidth() / 2 - 12, d.getHeight() / 2 + 40, "" + highScore, 30);
        d.drawText(d.getWidth() / 2 - 76, d.getHeight() / 2 + 180,
                "(press space to continue)", 15);

    }

}
