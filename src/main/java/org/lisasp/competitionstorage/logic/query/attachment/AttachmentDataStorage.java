package org.lisasp.competitionstorage.logic.query.attachment;

import lombok.RequiredArgsConstructor;
import org.lisasp.competitionstorage.logic.exception.AttachmentNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
@RequiredArgsConstructor
public class AttachmentDataStorage {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final AttachmentDataRepository repository;

    public byte[] fetchAttachmentData(String competitionId, String filename) {
        Optional<AttachmentDataEntity> data = repository.findById(AttachmentDataEntity.toId(competitionId, filename));
        if (data.isEmpty()) {
            throw new AttachmentNotFoundException(competitionId, filename);
        }
        return data.get().getData();
    }

    public Future<Void> storeAttachment(String competitionId, String filename, byte[] data) {
        return executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
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
                return null;
            }
        });
    }
}
