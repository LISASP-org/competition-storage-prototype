package org.lisasp.competitionstorage.logic.query.attachment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, String> {
    List<AttachmentEntity> findByCompetitionId(String competitionId);
    Optional<AttachmentEntity> findByCompetitionIdAndFilename(String competitionId, String filename);
}
