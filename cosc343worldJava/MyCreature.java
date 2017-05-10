import cosc343.assig2.Creature;
import chromosome.Chromosome;
import java.util.*;

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

      float sum = 0;
      for(int i = 0; i < numPercepts; i++) {
        if(percepts[i] > 0) sum++;
      }

      float[] temp = new float[numPercepts];
      Arrays.fill(temp, 0f);

      /* Percepts for Monsters. */
      for(int i = 0; i < VISIBLE_SQUARES; i++) {
        temp[i] += percepts[i] * chromosome.preferenceForMonsters();
        temp[VISIBLE_SQUARES - i - 1] -= percepts[i] * chromosome.preferenceForMonsters();
      }
      /* Percepts for other creatures. */
      for(int i = VISIBLE_SQUARES; i < VISIBLE_SQUARES * 2; i++) {
        temp[i] += percepts[i] * chromosome.preferenceForFriends();
        temp[VISIBLE_SQUARES * 2 - i - 1] -= percepts[i] * chromosome.preferenceForFriends();
      }
      /* Percepts for food. */
      for(int i = VISIBLE_SQUARES * 2; i < numPercepts; i++) {
        if(percepts[i] == 1) {
          temp[i] += chromosome.preferenceForGreen();
          temp[numPercepts - 1 - i] -= chromosome.preferenceForGreen();
        }
        if(percepts[i] == 2) {
          temp[i] += chromosome.preferenceForRed();
          temp[numPercepts - 1 - i] -= chromosome.preferenceForRed();
        }
      }

      float actions[] = new float[numExpectedActions];

      /* Sum of relevant weighted preferences. */
      for(int i = 0; i < VISIBLE_SQUARES; i++) {
        actions[i] = temp[i] + temp[i + VISIBLE_SQUARES] + temp[i + 2 * VISIBLE_SQUARES];
        temp[i] = actions[i];
      }

      /* Eat. */
      actions[numExpectedActions - 2] = 0;
      if(percepts[chromosome.whichSquare() + 2 * VISIBLE_SQUARES] == 2){
        actions[numExpectedActions - 2] = chromosome.preferenceForFood() + chromosome.preferenceForEatingRed();
      }
      if(percepts[chromosome.whichSquare() + 2 * VISIBLE_SQUARES] == 1){
        actions[numExpectedActions - 2] = chromosome.preferenceForFood() + chromosome.preferenceForEatingGreen();
      }

      float probabilityOfExploration = 0.05f;
      if(chromosome.random.nextFloat() < probabilityOfExploration) {
        actions[numExpectedActions - 1] = chromosome.explore();
      }


      String result = "";
      for(int i = 0; i < actions.length; i++) {
        result += actions[i] + " ";
      }
    //   System.out.println(result);

      return actions;
  }
}
