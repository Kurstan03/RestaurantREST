package peaksoft.exception;

/**
 * @author kurstan
 * @created at 17.03.2023 2:38
 */
public class NoValidException extends RuntimeException{
    public NoValidException() {
        super();
    }

    public NoValidException(String message) {
        super(message);
    }
}
