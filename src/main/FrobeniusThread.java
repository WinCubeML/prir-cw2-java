package main;

public class FrobeniusThread implements Runnable {
  private Matrix matrix;
  private int thread;
  private Pair<Integer, Integer> range;
  private double[] result;

  public FrobeniusThread(Matrix matrix, int thread, Pair<Integer, Integer> range, double[] result) {
    this.matrix = matrix;
    this.thread = thread;
    this.range = range;
    this.result = result;
  }

  @Override
  public void run() {

  }
}
