package exceptions;

public class MedicalRecorConflictException extends RuntimeException {
    public MedicalRecorConflictException(String message) {
        super(message);
    }
}
