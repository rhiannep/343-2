import java.util.*;

public class Plotter {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in).useDelimiter("\n\n");
    while(scanner.hasNext()) {
      Scanner lineScanner = new Scanner(scanner.next().replace(":", ""));
      lineScanner.next();
      if(args.length > 0) {
        System.out.println(lineScanner.nextInt() + " " + lineScanner.nextFloat());
      } else {
        lineScanner.nextInt();
        System.out.println(lineScanner.nextFloat());
      }
    }
  }
}
