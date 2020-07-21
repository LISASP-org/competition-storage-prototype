package org.lisasp.competitionstorage.logic.query.attachment;

import lombok.Getter;
import lombok.Setter;
import org.lisasp.competitionstorage.dto.AttachmentDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

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

    @Getter
    @Setter
    private String status = "new";

    public AttachmentEntity() {
    }

    public AttachmentEntity(@NotBlank String competitionId, @NotBlank String attachmentId, @NotBlank String filename) {
        this.id = attachmentId;
        this.competitionId = competitionId;
        this.filename = filename;
    }

    public AttachmentDto extractDto() {
        return new AttachmentDto(competitionId, filename);
    }
}
