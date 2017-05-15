import cosc343.assig2.Creature;
import chromosome.Chromosome;
import java.util.*;

/**
 * The MyCreate extends the cosc343 assignment 2 Creature.  Here you implement
 * creatures chromosome and the agent function that maps creature percepts to
 * actions.
 *
 * @author Rhianne Price
 * @version 1.0
 * @since   2017-04-05
 */
public class MyCreature extends Creature {
  private static final int VISIBLE_SQUARES = 9;
  public Chromosome chromosome;
  private Random random = new Random();

  /**
   * Default constructor generates a creature with a random chromosome.
   */
  public MyCreature() {
    this.chromosome = new Chromosome(random);
  }

  /**
   * Constructor generates a new chromosome adapted from two parent creatures.
   * Implementation of crossover and mutation is elsewhere: in the Chromosome
   * class.
   * @param mum the first parent make a new chromosome out of.
   * @param dad the second parent make a new chromosome out of.
   */
  public MyCreature(MyCreature mum, MyCreature dad) {
    this.chromosome = new Chromosome(mum.chromosome, dad.chromosome, random);
  }

  /**
   * Implementation of a creatures agent function. uses weighted sums to map
   * percepts onto actions.
   * @param percepts the array of things the creature can detect. Expecting
   *        percept format 2.
   * @param numPercepts the number of percepts this agent function is expecting.
   * @param numExpectedActions the number of actions the world expects to
   *        this function to return.
   */
  @Override
  public float[] AgentFunction(int[] percepts, int numPercepts, int numExpectedActions) {
      float actions[] = new float[numExpectedActions];
      float[] temp = new float[numPercepts];

      /* For each square, determine the preference for that square in terms of
         visible monsters. */
      for(int i = 0; i < VISIBLE_SQUARES; i++) {
        temp[i] = percepts[i] * chromosome.preferenceForMonsters();
      }
      /* For each square, determine the preference for that square in terms of
         visible creatures. */
      for(int i = VISIBLE_SQUARES; i < VISIBLE_SQUARES * 2; i++) {
        temp[i] = percepts[i] * chromosome.preferenceForFriends();
      }

      /* For each square, determine the preference for that square in terms of
         visible food. */
      for(int i = VISIBLE_SQUARES * 2; i < numPercepts; i++) {
        temp[i] = percepts[i];
        if(percepts[i] == 1) {
          temp[i] = chromosome.preferenceForGreen();
        }
        if(percepts[i] == 2) {
          temp[i] = chromosome.preferenceForRed();
        }
      }

      /* For each square, sum the total preferences. */
      for(int i = 0; i < VISIBLE_SQUARES; i++) {
        actions[i] = temp[i] + temp[i + VISIBLE_SQUARES] + temp[i + 2 * VISIBLE_SQUARES];
        temp[i] = actions[i];
      }

      /* If food is detected, determine how bad you want to eat it. */
      if(percepts[chromosome.whichSquare() + 2 * VISIBLE_SQUARES] == 2){
        actions[numExpectedActions - 2] = chromosome.preferenceForEatingRed();
      }
      if(percepts[chromosome.whichSquare() + 2 * VISIBLE_SQUARES] == 1){
        actions[numExpectedActions - 2] = chromosome.preferenceForEatingGreen();
      }

      /* Make an explorative move with a low probability. */
      float probabilityOfExploration = 0.05f;
      if(chromosome.random.nextFloat() < probabilityOfExploration) {
        actions[numExpectedActions - 1] = chromosome.explore();
      }

      return actions;
  }
}
