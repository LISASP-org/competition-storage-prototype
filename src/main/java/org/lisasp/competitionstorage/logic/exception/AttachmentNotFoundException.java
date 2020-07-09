package org.lisasp.competitionstorage.logic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AttachmentNotFoundException extends RuntimeException {

    public AttachmentNotFoundException(String id, String filename) {
        super(String.format("Attachment \"%s\" for Competition \"%s\" not found.", filename, id));
    }
}
