package exceptions;

/**
 * This class can be used to consolidate various data-access related
 * exceptions into one.
 * @author jlombardo
 */
public class DataAccessException extends Exception {

    private static final long serialVersionUID = 1L;

    public DataAccessException() {
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
