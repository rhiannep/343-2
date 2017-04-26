import cosc343.assig2.Creature;
import java.util.Random;
import Chromosome;

/**
* The MyCreate extends the cosc343 assignment 2 Creature.  Here you implement
* creatures chromosome and the agent function that maps creature percepts to
* actions.
*
* @author
* @version 1.0
* @since   2017-04-05
*/
public class MyCreature extends Creature {
  private static final int VISIBLE_SQUARES = 9;
  // Random number generator
  Random rand = new Random();

  Chromosome chromosome;

  /* Empty constructor - might be a good idea here to put the code that
   initialises the chromosome to some random state

   Input: numPercept - number of percepts that creature will be receiving
          numAction - number of action output vector that creature will need
                      to produce on every turn
  */
  public MyCreature(Chromosome chromosome) {
    this.chromosome = chromosome;
  }

  /* This function must be overridden by MyCreature, because it implements
     the AgentFunction which controls creature behavoiur.  This behaviour
     should be governed by a model (that you need to come up with) that is
     parameterise by the chromosome.

     Input: percepts - an array of percepts
            numPercepts - the size of the array of percepts depend on the percept
                          chosen
            numExpectedAction - this number tells you what the expected size
                                of the returned array of percepts should bes
     Returns: an array of actions
  */
  @Override
  public float[] AgentFunction(int[] percepts, int numPercepts, int numExpectedActions) {
      // the percepts.  You need to replace this code.

      double[] temp = new double[numPercepts];

      for(int i = 0; i < numPercepts; i++) {
         if(i < VISIBLE_SQUARES) {
           temp[i] = percepts[i] * chromosome.preferenceForMonsters();
         } else if(i < VISIBLE_SQUARES * 2) {
           temp[i] = percepts[i] * chromosome.preferenceForFriends();
         } else {
           temp[i] = percepts[i] * chromosome.preferenceForFood();
         }
      }
      float actions[] = new float[numExpectedActions];

      for(int i = 0; i < VISIBLE_SQUARES; i++) {
        actions[i] = temp[i] + temp[i + VISIBLE_SQUARES] + temp[i + 2 * VISIBLE_SQUARES];
      }
      actions[numExpectedActions - 2] = (float) percepts[chromosome.squareGuess] * chromosome.preferenceForEating();
      actions[numExpectedActions - 1] = (float) chromosome.nextDouble(-1, 1, rand);
      return actions;
  }
}
