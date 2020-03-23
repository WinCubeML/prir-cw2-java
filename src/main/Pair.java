package main;

public class Pair<A, B> {
  private A first;
  private B second;

  public Pair(A first, B second) {
    this.first = first;
    this.second = second;
  }

  public A getFirst() {
    return this.first;
  }

  public B getSecond() {
    return this.second;
  }

  @Override
  public String toString() {
    return "{" + first + "," + second + "}";
  }
}
