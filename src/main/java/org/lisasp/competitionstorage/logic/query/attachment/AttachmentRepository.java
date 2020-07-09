package org.lisasp.competitionstorage.logic.query.attachment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, String> {
    List<AttachmentEntity> findByCompetitionId(String competitionId);
}
