package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Matrix {
  private int rows;
  private int columns;
  private Double[] data;

  public Matrix(final int rows, final int columns, final Double[] data) {
    if (data.length != rows * columns) {
      throw new IllegalArgumentException("Wrong data length");
    }
    this.rows = rows;
    this.columns = columns;
    this.data = data;
  }

  public int getRowsCount() {
    return rows;
  }

  public int getColumnsCount() {
    return columns;
  }

  public Double[] getData() {
    return data;
  }

  public static Matrix blankMatrix(int rows, int columns) {
    return new Matrix(rows, columns, new Double[rows * columns]);
  }

  public static Matrix loadFile(String fileName) throws FileNotFoundException, InvalidFileFormatException {
    File file = new File(fileName);
    Scanner scanner = new Scanner(file);
    try {
      final int rows = scanner.nextInt();
      final int columns = scanner.nextInt();
      Matrix matrix = new Matrix(rows, columns, new Double[rows * columns]);

      for (int row = 0; row < rows; row++) {
        for (int column = 0; column < columns; column++) {
          matrix.data[matrix.getIndex(row, column)] = scanner.nextDouble();
        }
      }

      return matrix;
    } catch (NoSuchElementException e) {
      throw new InvalidFileFormatException("Bad file format");
    }
  }

  public Double get(int row, int column) {
    return this.data[getIndex(row, column)];
  }

  public void set(int row, int column, Double value) {
    this.data[getIndex(row, column)] = value;
  }

  public int getIndex(int row, int column) {
    return row * this.columns + column;
  }

  public Pair<Integer, Integer> mapIndexToRowAndColumn(int index) {
    return new Pair<>(index / this.rows, index % this.columns);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < this.rows; row++) {
      for (int column = 0; column < this.columns; column++) {
        sb.append(String.format("%10.6f   ", this.data[getIndex(row, column)]));
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
