package org.lisasp.competitionstorage.logic.exception;

public class DtoNotSpecifiedException extends RuntimeException {

    public DtoNotSpecifiedException(String id) {
        super(String.format("Dto for Competition \"%s\" not specified.", id));
    }

    public DtoNotSpecifiedException() {
        super("Dto not specified.");
    }
}
