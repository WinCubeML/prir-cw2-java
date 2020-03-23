package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utilities {

  /**
   * Utility to split data into ranges for multiple threads
   * Usage: range(12, 4) splits into ranges of: [0,3);[3,6);[6,9);[9,12)
   *
   * @param sizeOfData the size of data to be split
   * @param ranges     number of splits to be done
   * @return list of pairs (begin,end) representing ranges
   */
  public static List<Pair<Integer, Integer>> makeRanges(int sizeOfData, int ranges) {
    List<Pair<Integer, Integer>> rangesList = new ArrayList<>();
    int sizeOfRange = (int) Math.ceil((double) sizeOfData / (double) ranges);
    for (int i = 0, currentIndex = 0; i < ranges; i++) {
      if (currentIndex < sizeOfData) {
        Pair<Integer, Integer> range = new Pair<>(currentIndex, Math.min(currentIndex + sizeOfRange, sizeOfData));
        currentIndex = range.getSecond();
        rangesList.add(range);
      } else {
        rangesList.add(new Pair<>(0, 0));
      }
    }
    return rangesList;
  }

  /**
   * Utility to multiply two matrices with multithreading
   *
   * @param matrixA the matrix A
   * @param matrixB the matrix B
   * @param threads the thread count
   * @return the result matrix
   */
  public static Matrix multiplyMatrix(Matrix matrixA, Matrix matrixB, int threads) throws InterruptedException {
    if (matrixA.getColumnsCount() != matrixB.getRowsCount()) {
      throw new IllegalArgumentException("Ilość kolumn macierzy A nie jest równa ilości wierszy macierzy B. Tych " +
          "macierzy nie da się pomnożyć ze sobą.");
    }

    Matrix resultMatrix = Matrix.blankMatrix(matrixA.getRowsCount(), matrixB.getColumnsCount());
    List<Pair<Integer, Integer>> ranges = makeRanges(resultMatrix.getRowsCount() * resultMatrix.getColumnsCount(),
        threads);

    List<Thread> threadList = new ArrayList<>();
    for (int i = 0; i < threads; i++) {
      Thread thread = new Thread(new MultiplicationThread(matrixA, matrixB, resultMatrix, ranges.get(i)));
      thread.start();
      threadList.add(thread);
    }

    for (Thread thread : threadList) {
      thread.join();
    }
    return resultMatrix;
  }

  /**
   * Utility to get Frobenius Norm from given matrix with multithreading
   *
   * @param matrix  the matrix
   * @param threads the thread count
   * @return the Frobenius Norm
   */
  public static double getFrobeniusFromMatrix(Matrix matrix, int threads) throws InterruptedException {
    double[] resultArray = new double[threads];
    List<Pair<Integer, Integer>> ranges = makeRanges(matrix.getRowsCount() * matrix.getColumnsCount(), threads);

    List<Thread> threadList = new ArrayList<>();
    for (int i = 0; i < threads; i++) {
      Thread thread = new Thread(new FrobeniusThread(matrix, threads, ranges.get(i), resultArray));
      thread.start();
      threadList.add(thread);
    }

    for (Thread thread : threadList) {
      thread.join();
    }
    return Math.sqrt(Arrays.stream(resultArray).sum());
  }
}
