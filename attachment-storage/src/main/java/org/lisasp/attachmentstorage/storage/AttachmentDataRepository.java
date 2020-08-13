package org.lisasp.attachmentstorage.storage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface AttachmentDataRepository extends MongoRepository<AttachmentDataEntity, String> {
}
