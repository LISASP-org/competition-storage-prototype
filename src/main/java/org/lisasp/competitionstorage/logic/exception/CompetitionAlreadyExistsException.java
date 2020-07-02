package org.lisasp.competitionstorage.logic.exception;

public class CompetitionAlreadyExistsException extends RuntimeException {
    public CompetitionAlreadyExistsException(String id) {
        super(String.format("Competition \"%s\" already exists.", id));
    }
}
