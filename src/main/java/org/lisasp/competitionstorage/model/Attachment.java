package org.lisasp.competitionstorage.model;

import lombok.Getter;
import lombok.Setter;
import org.lisasp.competitionstorage.dto.AttachmentDto;
import org.lisasp.competitionstorage.logic.event.AttachmentAdded;
import org.lisasp.competitionstorage.logic.event.AttachmentUpdated;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class Attachment {

    @Getter
    @NotNull
    private String name;

    @Getter
    @NotNull
    private byte[] data;

    public Attachment(AttachmentAdded event) {
        name = event.getFilename();
        data = event.getData();
        validate();
    }

    public void on(AttachmentUpdated event) {
        data = event.getData();
        validate();
    }

    public void validate() {
        if (data == null) {
            data = new byte[0];
        }
    }
}
