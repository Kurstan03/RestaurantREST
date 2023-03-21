package peaksoft.exception;

/**
 * @author kurstan
 * @created at 17.03.2023 14:54
 */
public class NoVacancyException extends RuntimeException{
    public NoVacancyException() {
        super();
    }

    public NoVacancyException(String message) {
        super(message);
    }
}
