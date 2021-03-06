package org.lisasp.attachmentstorage.storage;

import lombok.RequiredArgsConstructor;
import org.lisasp.attachmentstorage.exception.AttachmentNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AttachmentDataStorage {

    private final AttachmentDataRepository repository;

    public byte[] fetchAttachmentData(String competitionId, String filename) {
        Optional<AttachmentDataEntity> data = repository.findById(AttachmentDataEntity.toId(competitionId, filename));
        if (data.isEmpty()) {
            throw new AttachmentNotFoundException(competitionId, filename);
        }
        return data.get().getData();
    }

    public void storeAttachment(String competitionId, String filename, byte[] data) {
        String id = AttachmentDataEntity.toId(competitionId, filename);
        Optional<AttachmentDataEntity> result = repository.findById(id);
        AttachmentDataEntity entity;
        if (result.isEmpty()) {
            entity = new AttachmentDataEntity(id, data);
        } else {
            entity = result.get();
        }
        entity.setData(data);
        repository.save(entity);
    }
}
