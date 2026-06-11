package exceptions;

public class MedicalRecordHasSurgeryException extends RuntimeException {
    public MedicalRecordHasSurgeryException(String message) {
        super(message);
    }
}
