package exceptions;

public class LoginConflictException extends RuntimeException {
    public LoginConflictException(String message) {
        super(message);
    }
}
