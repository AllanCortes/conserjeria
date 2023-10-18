package cl.ucn.disc.as.exceptions;

import javax.persistence.PersistenceException;

public class SistemaException extends RuntimeException {
    /**
     * The Constructor
     * @param message
     * @param ex
     */
    public SistemaException(String message, Long ex) {
        super();
    }
    public SistemaException(String message, PersistenceException ex) {
        super(message, ex);
    }

}
