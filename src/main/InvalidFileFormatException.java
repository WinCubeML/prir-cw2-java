package main;

public class InvalidFileFormatException extends Throwable {
  public InvalidFileFormatException(String message) {
    super(message);
  }
}
