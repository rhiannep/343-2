import java.util.*;

public class Plotter {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in).useDelimiter("\n\n");
    while(scanner.hasNext()) {
      Scanner lineScanner = new Scanner(scanner.next().replace(":", ""));
      lineScanner.next();
      System.out.println(lineScanner.nextInt() + " " + lineScanner.nextFloat());
    }
  }
}
