package org.lisasp.competitionstorage.logic.query.attachment;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.lisasp.competitionstorage.dto.AttachmentDto;
import org.lisasp.competitionstorage.logic.competition.AttachmentAdded;
import org.lisasp.competitionstorage.logic.competition.AttachmentRemoved;
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
    public void handle(AttachmentRemoved event, @Timestamp Instant timestamp) {
        repository.deleteById(event.getAttachmentId());
    }

    @QueryHandler
    public List<AttachmentDto> findByCompetitionId(AttachmentsPerCompetitionQuery query) {
        return repository.findByCompetitionId(query.getCompetitionId()).stream().map(a -> a.extractDto()).collect(Collectors.toList());
    }

    @QueryHandler
    public AttachmentDto findById(AttachmentFilenameQuery query) {
        Optional<AttachmentEntity> entity = repository.findByCompetitionIdAndFilename(query.getCompetitionId(), query.getFilename());
        if (entity.isEmpty()) {
            throw new AttachmentNotFoundException(query.getCompetitionId(), query.getFilename());
        }
        return entity.get().extractDto();
    }
}
