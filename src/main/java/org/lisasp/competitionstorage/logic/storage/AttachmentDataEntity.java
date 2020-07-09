package org.lisasp.competitionstorage.logic.storage;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class AttachmentDataEntity {

    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    @NotNull
    private byte[] data;

    public AttachmentDataEntity() {
    }

    public AttachmentDataEntity(@NotBlank String id, @NotNull byte[] data) {
        this.id = id;
        this.data = data;
    }
}
