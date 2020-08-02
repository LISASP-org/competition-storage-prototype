package org.lisasp.competitionstorage.logic.competition;

import lombok.EqualsAndHashCode;
import org.lisasp.competitionstorage.logic.exception.DataMissingException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@EqualsAndHashCode
public class Attachment {

    @NotNull
    private String id;

    @NotEmpty
    private String filename;

    private AttachmentStatus status = AttachmentStatus.New;

    Attachment(String filename) {
        this.filename = filename;
    }

    void on(AttachmentAdded event) {
        this.id = event.getAttachmentId();
        this.filename = event.getFilename();
    }

    void upload(UploadAttachment command){
        assertDataIsPresent(command);
        apply(new AttachmentUploaded(command.getCompeitionId(), command.getFilename()));
    }

    void on(AttachmentUploaded event) {
        status = AttachmentStatus.Uploaded;
    }

    public boolean matchesFilename(String filename) {
        if (filename == null) {
            return false;
        }
        return filename.equals(this.filename);
    }

    public void revoke(RemoveAttachment command) {
        apply(new AttachmentRemoved(command.getCompetitionId(), this.id, this.filename));
    }

    @Override
    public String toString() {
        return String.format("id: '%s', filename: '%s', status: '%s'", id, filename, status);
    }

    private void assertDataIsPresent(UploadAttachment command) {
        if (command.getData() == null || command.getData().length == 0) {
            throw new DataMissingException(command.getCompeitionId(), command.getFilename());
        }
    }
}
