package org.lisasp.competitionstorage.logic.exception;

public class IdMissingException extends RuntimeException {
    public IdMissingException() {
        super("No Id was given.");
    }
}
