package main;

public class MultiplicationThread implements Runnable {
  private Matrix matrixA;
  private Matrix matrixB;
  private Matrix resultMatrix;
  private Pair<Integer, Integer> range;

  public MultiplicationThread(Matrix matrixA, Matrix matrixB, Matrix resultMatrix, Pair<Integer, Integer> range) {
    this.matrixA = matrixA;
    this.matrixB = matrixB;
    this.resultMatrix = resultMatrix;
    this.range = range;
  }

  @Override
  public void run() {
    for (int index = range.getFirst(); index < range.getSecond(); index++) {
      Pair<Integer, Integer> rowAndColumn = resultMatrix.mapIndexToRowAndColumn(index);

      double sum = 0;
      for (int i = 0; i < matrixA.getColumnsCount(); i++) {
        double a = matrixA.get(rowAndColumn.getFirst(), i);
        double b = matrixB.get(i, rowAndColumn.getSecond());
        sum += a * b;
      }

      resultMatrix.set(rowAndColumn.getFirst(), rowAndColumn.getSecond(), sum);
    }
  }
}
