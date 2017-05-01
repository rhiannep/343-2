package chromosome;
import java.util.*;

public class Chromosome {
  private static final int VISIBLE_SQUARES = 9;
  private static final int MAX_PREFERENCE = 1;
  private static final int MIN_PREFERENCE = -1;

  public static final int MONSTERS = 0;
  public static final int RED = 1;
  public static final int GREEN = 2;
  public static final int FRIENDS = 3;
  public static final int EATING = 4;
  public static final int GUESS_A_SQUARE = 5;

  public static final int PARAMS = 6;

  public float[] preferences;
  public float[][] relativePreferences;

  private final Random random = new Random();

  public Chromosome() {
    preferences = new float[PARAMS];
    for(int i = 0; i < preferences.length - 1; i++) {
      preferences[i] = nextFloat();
    }
    preferences[GUESS_A_SQUARE] = random.nextInt(VISIBLE_SQUARES);

    relativePreferences = new float[VISIBLE_SQUARES][VISIBLE_SQUARES];
    for(int i = 0; i < VISIBLE_SQUARES; i++) {
      for(int j = 0; j < VISIBLE_SQUARES; j++){
        if(i != j) relativePreferences[i][j] = random.nextFloat();
      }
    }
  }

  public Chromosome(Chromosome mum, Chromosome dad) {
    preferences = new float[PARAMS];
    int crossover = 3;
    for(int i = 0; i < crossover; i++) {
      preferences[i] = mum.preferences[i];
    }
    for(int i = crossover; i < PARAMS; i++) {
      preferences[i] = dad.preferences[i];
    }

    float probabilityOfMutation = 0.1f;
    if(random.nextFloat() < probabilityOfMutation) {
      for(int i = 0; i < 3; i++) {
        preferences[random.nextInt(PARAMS - 1)] += nextFloat();
      }
    }

    if(random.nextFloat() < probabilityOfMutation/3f) {
      preferences[GUESS_A_SQUARE] = random.nextInt(VISIBLE_SQUARES);
    }

    relativePreferences = new float[VISIBLE_SQUARES][VISIBLE_SQUARES];
    for(int i = 0; i < VISIBLE_SQUARES; i++) {
      for(int j = 0; j < VISIBLE_SQUARES; j++){
        if(i == j) continue;
        if(j % 2 == 0) relativePreferences[i][j] = mum.relativePreferences[i][j];
        else relativePreferences[i][j] = dad.relativePreferences[i][j];
      }
    }

    if(random.nextFloat() < probabilityOfMutation) {
      for(int r = 0; r < 9; r++) {
          int i = random.nextInt(VISIBLE_SQUARES -1);
          int j = random.nextInt(VISIBLE_SQUARES -1);
        relativePreferences[i][j] += nextFloat();
        if(relativePreferences[i][j] < 0) relativePreferences[i][j] = 0f;
      }
    }


  }


  public float preferenceForMonsters() {
    return preferences[MONSTERS];
  }

  public float preferenceForRed() {
    return preferences[RED];
  }

  public float preferenceForGreen() {
    return preferences[GREEN];
  }

  public float preferenceForFriends() {
    return preferences[FRIENDS];
  }

  public float preferenceForEating() {
    return preferences[EATING];
  }

  public int whichSquare() {
    return (int) preferences[GUESS_A_SQUARE];
  }

  public float nextFloat() {
    return MIN_PREFERENCE + (MAX_PREFERENCE - MIN_PREFERENCE) * random.nextFloat();
  }

  public String toString() {
    String result = "";
    for(int i = 0; i < PARAMS; i++) {
      result += preferences[i] + " ";
    }

    result += "\n";

    for(int i = 0; i < VISIBLE_SQUARES; i++) {
     result += i + 1 + ": ";
      for(int j = 0; j < VISIBLE_SQUARES; j++) {
          result += relativePreferences[i][j] + " ";
      }
      result += "\n";
    }


    return result;
  }

}
