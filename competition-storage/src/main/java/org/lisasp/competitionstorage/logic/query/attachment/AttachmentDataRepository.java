package org.lisasp.competitionstorage.logic.query.attachment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentDataRepository extends JpaRepository<AttachmentDataEntity, String> {
}
