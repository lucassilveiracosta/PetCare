package exceptions;

public class WrongPasswordOrEmailException extends RuntimeException {
    public WrongPasswordOrEmailException(String message) {
        super(message);
    }
}
