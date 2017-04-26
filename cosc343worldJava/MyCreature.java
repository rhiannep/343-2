import cosc343.assig2.Creature;
import java.util.Random;

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

  // Random number generator
  Random rand = new Random();

  /* Empty constructor - might be a good idea here to put the code that
   initialises the chromosome to some random state

   Input: numPercept - number of percepts that creature will be receiving
          numAction - number of action output vector that creature will need
                      to produce on every turn
  */
  public MyCreature(int numPercepts, int numActions) {

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
      float actions[] = new float[numExpectedActions];
      for(int i=0;i<numExpectedActions;i++) {
         if(i < 9) {
           percepts[i] *= preferenceForMonsters;
         }
         if(i < 18) {
           percepts[i] *= preferenceForMonsters;
         }
      }
      return actions;
  }

}
