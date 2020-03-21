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

  public Matrix loadFile(String fileName) throws FileNotFoundException, InvalidFileFormatException {
    File file = new File(fileName);
    Scanner scanner = new Scanner(file);
    try {
      final int rows = scanner.nextInt();
      final int columns = scanner.nextInt();
      Matrix matrix = new Matrix(rows, columns, new Double[rows * columns]);

      for (int row = 0; row < rows; row++) {
        for (int column = 0; column < columns; column++) {
          matrix.data[getIndex(row, column)] = scanner.nextDouble();
        }
      }

      return matrix;
    } catch (NoSuchElementException e) {
      throw new InvalidFileFormatException("Bad file format");
    }
  }

  public int getIndex(int row, int column) {
    return row * this.columns + column;
  }

  public Double get(int row, int column) {
    return this.data[getIndex(row, column)];
  }

  public void set(int row, int column, Double value) {
    this.data[getIndex(row, column)] = value;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < this.rows; row++) {
      for (int column = 0; row < this.columns; column++) {
        sb.append(String.format("%6.2f   ", this.data[getIndex(row, column)]));
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
