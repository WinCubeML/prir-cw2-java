package main;

import java.io.FileNotFoundException;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    final int threadCount = getThreadCount(args);
    if (threadCount == -1) {
      System.exit(-1);
    }
    Matrix matrixA = null, matrixB = null;
    try {
      matrixA = Matrix.loadFile("A.txt");
      matrixB = Matrix.loadFile("B.txt");
    } catch (InvalidFileFormatException | FileNotFoundException e) {
      e.printStackTrace();
      System.exit(-2);
    }

    System.out.println("Macierz A:");
    System.out.println(matrixA.toString());

    System.out.println();

    System.out.println("Macierz B:");
    System.out.println(matrixB.toString());

    Matrix matrixC = Utilities.multiplyMatrix(matrixA, matrixB, threadCount);
    double frobenius = Utilities.getFrobeniusFromMatrix(matrixC, threadCount);

    System.out.println("Wynik mnożenia macierzy:");
    System.out.println(matrixC);

    System.out.println();

    System.out.println("Norma Frobeniusa macierzy wynikowej: " + frobenius);
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
