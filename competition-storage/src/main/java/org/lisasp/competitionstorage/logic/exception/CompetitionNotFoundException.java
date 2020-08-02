package org.lisasp.competitionstorage.logic.exception;

public class CompetitionNotFoundException extends RuntimeException {

    public CompetitionNotFoundException(String id) {
        super(String.format("Competition \"%s\" not found.", id));
    }
}
