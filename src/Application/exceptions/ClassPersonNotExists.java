package exceptions;

public class ClassPersonNotExists extends RuntimeException {
  public ClassPersonNotExists(String message) {
    super(message);
  }
}
