package peaksoft.exception;

/**
 * @author kurstan
 * @created at 17.03.2023 15:37
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
