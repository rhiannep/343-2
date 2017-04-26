import java.util.*;
import chromosome.*;

public class Test {
  public static Random rand = new Random();
  public static int VISIBLE_SQUARES = 9;
  public static void main(String[] args) {
    for(int j = 0; j < 30; j++) {
      int[] percepts = new int[9 * 3];
      for(int i = 0; i < percepts.length; i++) {
        percepts[i] = rand.nextInt(2);
      }
      float[] actions = AgentFunction(percepts, percepts.length, 11);
      int maxI = 0;
      for(int i = 0; i < actions.length; i++) {
        if(actions[i] > actions[maxI]) {
          maxI = i;
        }
        System.out.print(actions[i] + " ");
      }
      System.out.println();
      System.out.println(maxI);
    }
  }

  public static float[] AgentFunction(int[] percepts, int numPercepts, int numExpectedActions) {
      // the percepts.  You need to replace this code.
      Chromosome chromosome = new Chromosome(rand);
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
        actions[i] = (float) (temp[i] + temp[i + VISIBLE_SQUARES] + temp[i + 2 * VISIBLE_SQUARES]);
      }
      actions[numExpectedActions - 2] = (float) (percepts[chromosome.squareGuess] * chromosome.preferenceForEating());
      actions[numExpectedActions - 1] = (float) chromosome.nextDouble(-1, 1, rand);
      return actions;
  }
}
