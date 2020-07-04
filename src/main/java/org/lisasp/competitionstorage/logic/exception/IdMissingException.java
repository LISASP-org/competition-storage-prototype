package org.lisasp.competitionstorage.logic.exception;

public class IdMissingException extends RuntimeException {
    public IdMissingException() {
        super("No Id was given.");
    }

    public IdMissingException(String id) {
        super(String.format("Second id was not given for \"%s\".", id));
    }
}
