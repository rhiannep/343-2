import cosc343.assig2.Creature;
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
  private static final int MAX_PREFERENCE = 1;
  private static final int MIN_PREFERENCE = -1;

  public static final int MONSTERS = 0;
  public static final int FIND_RED = 1;
  public static final int FIND_GREEN = 2;
  public static final int EXPLORE = 3;
  public static final int FRIENDS = 4;
  public static final int EAT_RED = 5;
  public static final int EAT_GREEN = 6;

  public static final int GUESS_A_SQUARE = 7;

  public static final int PARAMS = 8;

  private float[] chromosome;

  private Random random = new Random();

  /**
   * Default constructor generates a creature with a random chromosome.
   */
  public MyCreature() {
      chromosome = new float[PARAMS];
      for(int i = 0; i < chromosome.length - 1; i++) {
        chromosome[i] = nextFloat();
      }
      chromosome[GUESS_A_SQUARE] = this.random.nextInt(VISIBLE_SQUARES);
  }

  /**
   * Constructor generates a new chromosome adapted from two parent creatures
   * Using crossover and mutation.
   * @param mum the first parent make a new chromosome out of.
   * @param dad the second parent make a new chromosome out of.
   */
  public MyCreature(MyCreature mum, MyCreature dad) {
      float probabilityOfMutation = 0.05f;
      chromosome = new float[PARAMS];

      /* Entirely fresh craeture sometimes generated. */
      if(this.random.nextFloat() < probabilityOfMutation) {
        for(int i = 0; i < chromosome.length - 1; i++) {
          chromosome[i] = nextFloat();
        }
        chromosome[GUESS_A_SQUARE] = this.random.nextInt(VISIBLE_SQUARES);
        return;
      }

      int crossover = 5;
      /* Take the mums first four parameters. */
      for(int i = 0; i < crossover; i++) {
        chromosome[i] = mum.chromosome[i];
      }

      /* Take the dads last four parameters. */
      for(int i = crossover; i < PARAMS; i++) {
        chromosome[i] = dad.chromosome[i];
      }

      /* Mutate three random parameters by a random fraction. */
      if(this.random.nextFloat() < probabilityOfMutation) {
        for(int i = 0; i < 3; i++) {
          chromosome[random.nextInt(PARAMS - 1)] += nextFloat();
        }
    }
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
        temp[i] = percepts[i] * chromosome[MONSTERS];
      }
      /* For each square, determine the preference for that square in terms of
         visible creatures. */
      for(int i = VISIBLE_SQUARES; i < VISIBLE_SQUARES * 2; i++) {
        temp[i] = percepts[i] * chromosome[FRIENDS];
      }

      /* For each square, determine the preference for that square in terms of
         visible food. */
      for(int i = VISIBLE_SQUARES * 2; i < numPercepts; i++) {
        temp[i] = percepts[i];
        if(percepts[i] == 1) {
          temp[i] = chromosome[FIND_GREEN];
        }
        if(percepts[i] == 2) {
          temp[i] = chromosome[FIND_RED];
        }
      }

      /* For each square, sum the total chromosome. */
      for(int i = 0; i < VISIBLE_SQUARES; i++) {
        actions[i] = temp[i] + temp[i + VISIBLE_SQUARES] + temp[i + 2 * VISIBLE_SQUARES];
        temp[i] = actions[i];
      }

      /* If food is detected, determine how badly you want to eat it. */
      if(percepts[(int) chromosome[GUESS_A_SQUARE] + 2 * VISIBLE_SQUARES] == 2){
        actions[numExpectedActions - 2] = chromosome[EAT_RED];
      }
      if(percepts[(int) chromosome[GUESS_A_SQUARE] + 2 * VISIBLE_SQUARES] == 1){
        actions[numExpectedActions - 2] = chromosome[EAT_GREEN];
      }

      /* Make an explorative move determined by preference for exploration. */
      actions[numExpectedActions - 1] = chromosome[EXPLORE];

      return actions;
  }

  private float nextFloat() {
    return MIN_PREFERENCE + (MAX_PREFERENCE - MIN_PREFERENCE) * random.nextFloat();
  }

  public String toString() {
    String result = " MONSTERS   FINDRED    FINDGREEN  EXPLORE  EAT RED    EAT GREEN   FRIENDS     SQUARE\n";
    for(int i = 0; i < PARAMS; i++) {
      result += chromosome[i] + " ";
    }
    return result;
  }
}
