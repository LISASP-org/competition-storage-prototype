package org.lisasp.competitionstorage.logic.exception;

public class AttachmentNotFoundException extends RuntimeException {

    public AttachmentNotFoundException(String id, String attachmentId) {
        super(String.format("Attachment \"%s\" for Competition \"%s\" not found.", attachmentId, id));
    }

    public AttachmentNotFoundException(String id, String attachmentId, String name) {
        super(String.format("Attachment \"%s\" with name \"%s\" for Competition \"%s\" not found.", attachmentId, name, id));
    }
}
