// ID: 584698174

package communication;

import animations.AnimationRunner;
import animations.HighScoreScreen;
import animations.KeypressStoppableAnimation;

import biuoop.KeyboardSensor;

/**
 * A task whose purpose is to run an animation that displays the
 * high score.
 * @author David Dinkevich
 */
public class ShowHiScoresTask implements Task<Void> {

   /** The AnimationRunner to run the animation. */
   private AnimationRunner runner;
   /** Reference to a keyboard sensor. */
   private KeyboardSensor sensor;

   /**
    * Instantiates a new object of this class.
    * @param runner the AnimationRunner to run the animation
    * @param sensor a reference to a KeyboardSensor to know when to end the animation
    */
   public ShowHiScoresTask(AnimationRunner runner, KeyboardSensor sensor) {
      this.runner = runner;
      this.sensor = sensor;
   }

   @Override
   public Void run() {
      runner.run(new KeypressStoppableAnimation(sensor, sensor.SPACE_KEY, new HighScoreScreen()));
      return null;
   }

}
