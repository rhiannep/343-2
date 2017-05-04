package chromosome;
import java.util.*;

public class Chromosome {
  private static final int VISIBLE_SQUARES = 9;
  private static final int MAX_PREFERENCE = 1;
  private static final int MIN_PREFERENCE = -1;

  public static final int MONSTERS = 0;
  public static final int FIND_RED = 1;
  public static final int FIND_GREEN = 2;
  public static final int EAT_RED = 3;
  public static final int EAT_GREEN = 4;
  public static final int FRIENDS = 5;
  public static final int GUESS_A_SQUARE = 6;

  public static final int PARAMS = 7;

  public float[] preferences;
  public HashMap<String, Float> hPreferences;

  public final Random random = new Random();

  public Chromosome() {
    preferences = new float[PARAMS];
    for(int i = 0; i < preferences.length - 1; i++) {
      preferences[i] = nextFloat();
    }
    preferences[GUESS_A_SQUARE] = random.nextInt(VISIBLE_SQUARES);
  }

  public Chromosome(Chromosome mum, Chromosome dad) {
    preferences = new float[PARAMS];
    int crossover = 4;
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
  }


  public float preferenceForMonsters() {
    return preferences[MONSTERS];
  }

  public float preferenceForRed() {
    return preferences[FIND_RED];
  }

  public float preferenceForGreen() {
    return preferences[FIND_GREEN];
  }

  public float preferenceForFriends() {
    return preferences[FRIENDS];
  }

  public float preferenceForEatingRed() {
    return preferences[EAT_RED];
  }

  public float preferenceForEatingGreen() {
    return preferences[EAT_GREEN];
  }

  public int whichSquare() {
    return (int) preferences[GUESS_A_SQUARE];
  }

  public float nextFloat() {
    return MIN_PREFERENCE + (MAX_PREFERENCE - MIN_PREFERENCE) * random.nextFloat();
  }

  public float explore() {
    float max = 0;
    for(float preference : preferences) {
      if(preference > max) max = preference;
    }
    return max + 1;
  }

  public String toString() {
    String result = "MONSTERS  FINDRED FINDGREEN    EAT RED    EAT GREEN   FRIENDS     SQUARE\n";
    for(int i = 0; i < PARAMS; i++) {
      result += preferences[i] + " ";
    }
    return result;
  }

}
