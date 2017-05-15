import cosc343.assig2.World;
import cosc343.assig2.Creature;
import java.util.*;

/**
* The MyWorld extends the cosc343 assignment 2 World.  Here you can set
* some variables that control the simulations and override functions that
* generate populations of creatures that the World requires for its
* simulations.
*
* @author
* @version 1.0
* @since   2017-04-05
*/
public class MyWorld extends World {

  /* Here you can specify the number of turns in each simulation
  * and the number of generations that the genetic algorithm will
  * execute.
  */
  private final int _numTurns = 100;
  private final int _numGenerations = 1000;
  private static final float[] FITNESS_PARAMS = { 15f, 30f, 10f };
  private static final Random random = new Random();

  /* Constructor.

  Input: gridSize - the size of the world
  windowWidth - the width (in pixels) of the visualisation window
  windowHeight - the height (in pixels) of the visualisation window
  repeatableMode - if set to true, every simulation in each
  generation will start from the same state
  perceptFormat - format of the percepts to use: choice of 1, 2, or 3
  */
  public MyWorld(int gridSize, int windowWidth, int windowHeight, boolean repeatableMode, int perceptFormat) {
    // Initialise the parent class - don't remove this
    super(gridSize, windowWidth,  windowHeight, repeatableMode, perceptFormat);

    // Set the number of turns and generations
    this.setNumTurns(_numTurns);
    this.setNumGenerations(_numGenerations);
  }

  /* The main function for the MyWorld application

  */
  public static void main(String[] args) {
    // Here you can specify the grid size, window size and whether to run
    // in repeatable mode or not
    int gridSize = 30;
    int windowWidth =  1600;
    int windowHeight = 900;
    boolean repeatableMode = true;

    /* Here you can specify percept format to use - there are three to
       chose from: 1, 2, 3.  Refer to the Assignment2 instructions for
       explanation of the three percept formats. */
    int perceptFormat = 2;

    // Instantiate MyWorld object.  The rest of the application is driven
    // from the window that will be displayed.
    MyWorld sim = new MyWorld(gridSize, windowWidth, windowHeight, repeatableMode, perceptFormat);
  }

  /**
   * Generates the first generation of creatures. The first generation have
   * randomly determined chromosome values. The work for this is in the
   * MyCreature class, where the random seed is set, so every creature has it's
   * own random seed.
   * @param numCreatures the number of creatures the world is expecting.
   * @return a starting generation of random creatures.
   */
  @Override
  public MyCreature[] firstGeneration(int numCreatures) {
    int numActions = this.expectedNumberofActions();
    MyCreature[] population = new MyCreature[numCreatures];
    for(int i = 0; i < numCreatures; i++) {
      population[i] = new MyCreature();
    }
    return population;
  }

  /**
   * Generates the next generation of creatures given the previous generation.
   * Takes the two highest scoring creatures and makes a new population of
   * creatures based on these two. The crossover of these two creatures is
   * implemented elsewhere, int the MyCreature class. This function also prints
   * the average fitness and number of survivors for each generation.
   * @param old_population_btc the preveious generation of creatures.
   * @param numCreatures the number of creatures the world is expecting.
   * @return a new generation of creatures bred from the given previous generation.
   */
  @Override
  public MyCreature[] nextGeneration(Creature[] old_population_btc, int numCreatures) {
    MyCreature[] old_population = (MyCreature[]) old_population_btc;
    MyCreature[] new_population = new MyCreature[numCreatures];

    float avgLifeTime = 0f;
    int nSurvivors = 0;
    float averageFitness = 0f;

    MyCreature king = old_population[0];

    for(MyCreature creature : old_population) {
      int energy = creature.getEnergy();
      boolean dead = creature.isDead();
      float fitness = fitness(creature);
      averageFitness += fitness;

      if(dead) {
        int timeOfDeath = creature.timeOfDeath();
        avgLifeTime += (float) timeOfDeath;
      } else {
        nSurvivors += 1;
        avgLifeTime += (float) _numTurns;
      }

      if(fitness > fitness(king)) {
        king = creature;
      }
    }

    Random roulette = new Random();
    float rouletteSpin1 = roulette.nextFloat();
    float rouletteSpin2 = roulette.nextFloat();
    MyCreature winner = king;
    MyCreature runnerUp = king;

    float current = 0f;

    for(MyCreature creature : old_population) {
      float standardisedFitness = fitness(creature) / averageFitness;
      if(rouletteSpin1 >= current && rouletteSpin1 < current + standardisedFitness) {
        winner = creature;
      }
      if(rouletteSpin2 >= current && rouletteSpin2 < current + standardisedFitness) {
        runnerUp = creature;
      }
    }

    averageFitness /= (float) numCreatures;

    System.out.println("  Average Fitness: " + averageFitness);
    System.out.println("  Survivors      : " + nSurvivors + " out of " + numCreatures);

    for(int i = 0; i < numCreatures; i++) {
      if(i % 10 == 0) {
        new_population[i] = king;
      } else {
        new_population[i] = new MyCreature(winner, runnerUp);
      }
    }
    return new_population;
  }

  /**
   * Fitness function determines the fitness of a creature using a weighted
   * average of energy level, lifespan, and survival status. Weights are
   * determined in FITNESS_PARAMS and normalized here.
   * @param creature the creature to calculate the fitness for.
   * @return the final fitness score for the given creature.
   */
  private float fitness(Creature creature) {
    float sumOfWeights = 0f;

    for(int i = 0; i < FITNESS_PARAMS.length; i++) {
      sumOfWeights += FITNESS_PARAMS[i];
    }

    for(int i = 0; i < FITNESS_PARAMS.length; i++) {
      FITNESS_PARAMS[i] /= sumOfWeights;
    }

    float fitness = 0f;

    if(creature.isDead()) {
      fitness += FITNESS_PARAMS[1] * creature.timeOfDeath();
      fitness += FITNESS_PARAMS[0] * creature.getEnergy();
    } else {
      fitness += FITNESS_PARAMS[0] * (1 + FITNESS_PARAMS[2]) * creature.getEnergy();
      fitness += FITNESS_PARAMS[1] * 100f;
      fitness += FITNESS_PARAMS[2];
    }
    return fitness;
  }
}
