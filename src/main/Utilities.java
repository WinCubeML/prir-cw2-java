package main;

import java.util.ArrayList;
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
  public static List<Pair<Integer, Integer>> range(int sizeOfData, int ranges) {
    List<Pair<Integer, Integer>> rangesList = new ArrayList<>(ranges);
    int sizeOfRange = (int) Math.ceil((double) sizeOfData / (double) ranges);
    for (int i = 0, currentIndex = 0; i < ranges; i++) {
      if (currentIndex < sizeOfData) {
        // TODO
      } else {
        rangesList.add(new Pair<>(0, 0));
      }
    }
    return rangesList;
  }
}
