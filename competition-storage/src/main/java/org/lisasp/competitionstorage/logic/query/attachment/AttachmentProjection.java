package org.lisasp.competitionstorage.logic.query.attachment;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.jetbrains.annotations.NotNull;
import org.lisasp.competitionstorage.dto.AttachmentDto;
import org.lisasp.competitionstorage.logic.competition.AttachmentAdded;
import org.lisasp.competitionstorage.logic.competition.AttachmentRemoved;
import org.lisasp.competitionstorage.logic.competition.AttachmentUploaded;
import org.lisasp.competitionstorage.logic.exception.AttachmentNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AttachmentProjection {

    private final AttachmentRepository repository;

    @EventHandler
    public void handle(AttachmentAdded event, @Timestamp Instant timestamp) {
        repository.save(new AttachmentEntity(event.getCompetitionId(), event.getAttachmentId(), event.getFilename()));
    }

    @EventHandler
    public void handle(AttachmentUploaded event, @Timestamp Instant timestamp) {
        AttachmentEntity attachment = findByFilename(event.getCompetitionId(), event.getFilename());
        attachment.setStatus("uploaded");
        repository.save(attachment);
    }

    @EventHandler
    public void handle(AttachmentRemoved event, @Timestamp Instant timestamp) {
        repository.deleteById(event.getAttachmentId());
    }

    @QueryHandler
    public List<AttachmentDto> findByCompetitionId(AttachmentsPerCompetitionQuery query) {
        return repository.findByCompetitionId(query.getCompetitionId()).stream().map(a -> a.extractDto()).collect(Collectors.toList());
    }

    @QueryHandler
    public AttachmentDto findByFilename(AttachmentIdQuery query) {
        Optional<AttachmentEntity> entity = repository.findById(query.getAttachmentId());
        if (entity.isEmpty()) {
            throw new AttachmentNotFoundException(query.getAttachmentId());
        }
        return entity.get().extractDto();
    }

    @QueryHandler
    public AttachmentDto findByFilename(AttachmentFilenameQuery query) {
        AttachmentEntity entity = findByFilename(query.getCompetitionId(), query.getFilename());
        return entity.extractDto();
    }

    @NotNull
    private AttachmentEntity findByFilename(String competitionId, String filename) {
        Optional<AttachmentEntity> entity = repository.findByCompetitionIdAndFilename(competitionId, filename);
        if (entity.isEmpty()) {
            throw new AttachmentNotFoundException(competitionId, filename);
        }
        return entity.get();
    }
}
