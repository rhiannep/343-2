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
  public static final int EATING = 6;
  public static final int GUESS_A_SQUARE = 7;

  public static final int PARAMS = 8;

  public float[] preferences;
  public HashMap<String, Float> hPreferences;

  private final Random random = new Random();

  public Chromosome() {
    preferences = new float[PARAMS];
    for(int i = 0; i < preferences.length - 1; i++) {
      preferences[i] = nextFloat();
    }
    preferences[GUESS_A_SQUARE] = random.nextInt(VISIBLE_SQUARES);

    relativePreferences = new HashMap<String, Float>();
    for(int i = 0; i < VISIBLE_SQUARES; i++) {
      for(int j = i; j < VISIBLE_SQUARES; j++){
        relativePreferences.put(i + "," + j, random.nextFloat());
      }
    }
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

    relativePreferences = new HashMap<String, Float>();
    for(int i = 0; i < VISIBLE_SQUARES; i++) {
      for(int j = i; j < VISIBLE_SQUARES; j++){
        if(j % 2 == 0) {
          relativePreferences.put(i + "," + j, mum.relativePreferences.get(i + "," + j));
        } else {
          relativePreferences.put(i + "," + j, dad.relativePreferences.get(i + "," + j));
        }
      }
    }

    if(random.nextFloat() < probabilityOfMutation) {
      for(int r = 0; r < 9; r++) {
          int i = random.nextInt(VISIBLE_SQUARES -1);
          int j = random.nextInt(VISIBLE_SQUARES -1);
          if(j < i) {
            int t = j;
            j = i;
            i = t;
          }
        relativePreferences.put(i + "," + j, relativePreferences.get(i + "," + j) + nextFloat());
        if(relativePreferences.get(i + "," + j) < 0) relativePreferences.put(i + "," + j, 0f);
      }
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

  public String toString() {
    String result = "";
    for(int i = 0; i < PARAMS; i++) {
      result += preferences[i] + " ";
    }

    result += "\n";

    result += relativePreferences.toString();


    return result;
  }

}
