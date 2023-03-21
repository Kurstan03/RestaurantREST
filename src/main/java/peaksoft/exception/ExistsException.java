package peaksoft.exception;

/**
 * @author kurstan
 * @created at 17.03.2023 2:35
 */
public class ExistsException extends RuntimeException{
    public ExistsException() {
        super();
    }

    public ExistsException(String message) {
        super(message);
    }
}
