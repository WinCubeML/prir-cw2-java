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

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getColumns() {
    return columns;
  }

  public void setColumns(int columns) {
    this.columns = columns;
  }

  public Double[] getData() {
    return data;
  }

  public void setData(Double[] data) {
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

  private int getIndex(int row, int column) {
    return row * this.columns + column;
  }
}
