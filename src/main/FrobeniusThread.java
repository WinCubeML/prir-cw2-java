package main;

public class FrobeniusThread implements Runnable {
  private Matrix matrix;
  private int thread;
  private Pair<Integer, Integer> range;
  private double[] resultArray;

  public FrobeniusThread(Matrix matrix, int thread, Pair<Integer, Integer> range, double[] resultArray) {
    this.matrix = matrix;
    this.thread = thread;
    this.range = range;
    this.resultArray = resultArray;
  }

  @Override
  public void run() {
    double sum = 0;
    for (int index = range.getFirst(); index < range.getSecond(); index++) {
      Pair<Integer, Integer> rowAndColumn = matrix.mapIndexToRowAndColumn(index);
      sum += Math.pow(matrix.get(rowAndColumn.getFirst(), rowAndColumn.getSecond()), 2);
    }
    resultArray[thread] = sum;
  }
}
