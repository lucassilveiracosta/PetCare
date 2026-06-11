package exceptions;

/** REQ19 - Raised when an animal does not meet the minimum age for a vaccination protocol. */
public class InvalidAnimalAgeException extends RuntimeException {
    public InvalidAnimalAgeException(String message) {
        super(message);
    }
}
