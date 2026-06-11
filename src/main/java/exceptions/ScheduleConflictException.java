package exceptions;

/** REQ04/REQ05 - Raised when a professional already has a booking at the same date/time. */
public class ScheduleConflictException extends RuntimeException {
    public ScheduleConflictException(String message) {
        super(message);
    }
}
