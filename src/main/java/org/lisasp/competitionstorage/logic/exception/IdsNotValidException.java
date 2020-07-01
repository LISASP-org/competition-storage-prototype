package org.lisasp.competitionstorage.logic.exception;

public class IdsNotValidException extends RuntimeException {
    public IdsNotValidException(String id1, String id2) {
        super(String.format("Ids do not match (\"%s\" <> \"%s\")", id1, id2));
    }
}
