package exceptions;

/** REQ17 - Raised when trying to delete a medical record that already has clinical entries. */
public class MedicalRecordDeletionException extends RuntimeException {
    public MedicalRecordDeletionException(String message) {
        super(message);
    }
}
