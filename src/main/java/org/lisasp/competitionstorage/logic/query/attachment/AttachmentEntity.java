package org.lisasp.competitionstorage.logic.query.attachment;

import lombok.Getter;
import lombok.Setter;
import org.lisasp.competitionstorage.dto.AttachmentDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class AttachmentEntity {

    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String competitionId;

    @Getter
    @Setter
    private String filename;

    public AttachmentEntity() {
    }

    public AttachmentEntity(@NotBlank String competitionId, @NotBlank String filename) {
        this.id = toId(competitionId, filename);
        this.competitionId = competitionId;
        this.filename = filename;
    }

    public static String toId(@NotBlank String competitionId, @NotBlank String filename) {
        return String.format("%s|%s", competitionId, filename);
    }

    public AttachmentDto extractDto() {
        return new AttachmentDto(competitionId, filename);
    }
}
