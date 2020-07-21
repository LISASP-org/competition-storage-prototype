package org.lisasp.competitionstorage.logic.exception;

import java.io.IOException;

public class CouldNotSerializeException extends RuntimeException {

    public CouldNotSerializeException(IOException ex) {
        super("Could not serialize", ex);
    }
}
