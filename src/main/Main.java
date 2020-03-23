package main;

public class Main {
  public static void main(String[] args) {
    final int threadCount = getThreadCount(args);
    if (threadCount == -1) {
      System.exit(-1);
    }
  }

  /**
   * Get thread count from runner args
   *
   * @param args the runner args
   * @return the thread count (if returned -1: exit program in Main)
   */
  private static int getThreadCount(String[] args) {
    if (args.length < 1) {
      System.out.println("Proszę podać liczbę wątków do wykonania obliczeń.");
      return -1;
    }

    try {
      int threads = Integer.parseInt(args[0]);
      if (threads < 1) {
        throw new NumberFormatException();
      }

      return threads;
    } catch (NumberFormatException e) {
      System.out.println("Podana liczba wątków jest nieprawidłowa");
      return -1;
    }
  }
}
