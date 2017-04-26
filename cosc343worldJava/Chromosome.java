import java.util.*;

public class Chromosome {
  private static final int VISIBLE_SQUARES = 9;
  private static final int MAX_PREFERENCE = 1;
  private static final int MIN_PREFERENCE = -1;

  public static final int MONSTERS = 0;
  public static final int FOOD = 1;
  public static final int RED_OR_GREEN = 2;
  public static final int FRIENDS = 3;
  public static final int EATING = 4;


  public double[] preferences;

  public int squareGuess;

  public Chromosome(Random random) {
    preferences = new double[EATING + 1];
    for(int i = 0; i < preferences.length; i++) {
      preferences[i] = nextDouble(MIN_PREFERENCE, MAX_PREFERENCE, random);
    }
    squareGuess = random.nextInt(VISIBLE_SQUARES + 1);
  }

  public Chromosome(Chromosome mum, Chromosome dad) {
    
  }

  public double preferenceForMonsters() {
    return preferences[MONSTERS];
  }

  public double preferenceForFood() {
    return preferences[FOOD];
  }

  public double preferenceForRed() {
    return preferences[RED_OR_GREEN];
  }

  public double preferenceForFriends() {
    return preferences[FRIENDS];
  }

  public double preferenceForEating() {
    return preferences[EATING];
  }

  public double nextDouble(double min, double max, Random random) {
    return min + (max - min) * random.nextDouble();
  }

}
