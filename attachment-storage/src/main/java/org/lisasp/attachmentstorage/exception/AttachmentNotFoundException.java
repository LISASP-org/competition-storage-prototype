package org.lisasp.attachmentstorage.exception;

public class AttachmentNotFoundException extends RuntimeException {

    public AttachmentNotFoundException(String competitionId, String filename) {
        super(String.format("Attachment with filename \"%s\" was not found for competition \"%s\".", filename, competitionId));
    }

    public AttachmentNotFoundException(String attachmentId) {
        super(String.format("Attachment \"%s\" was not found.", attachmentId));
    }
}
