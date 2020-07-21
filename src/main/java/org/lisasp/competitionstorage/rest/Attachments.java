package org.lisasp.competitionstorage.rest;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.messaging.responsetypes.InstanceResponseType;
import org.axonframework.messaging.responsetypes.MultipleInstancesResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.lisasp.competitionstorage.dto.*;
import org.lisasp.competitionstorage.logic.competition.AddAttachment;
import org.lisasp.competitionstorage.logic.competition.RemoveAttachment;
import org.lisasp.competitionstorage.logic.competition.UploadAttachment;
import org.lisasp.competitionstorage.logic.query.attachment.AttachmentFilenameQuery;
import org.lisasp.competitionstorage.logic.query.attachment.AttachmentsPerCompetitionQuery;
import org.lisasp.competitionstorage.logic.query.attachment.AttachmentDataStorage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController()
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class Attachments {

    private final QueryGateway gateway;
    private final CommandGateway commandGateway;
    private final AttachmentDataStorage storage;

    private final IdentifierFactory identifierFactory = IdentifierFactory.getInstance();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @PostMapping("/")
    Future<Void> addAttachment(@RequestBody AddAttachmentDto attachment, @PathVariable String competitionId) {
        return commandGateway.send(new AddAttachment(competitionId, identifierFactory.generateIdentifier(), attachment.getFilename()));
    }

    @PutMapping("/{competitionId}/{filename}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> updateAttachment(@RequestBody UploadAttachmentDto attachment, @PathVariable String competitionId, @PathVariable String filename) {
        return executor.submit(() -> {
            commandGateway.send(new UploadAttachment(competitionId, filename, attachment.getData()));
            storage.storeAttachment(competitionId, filename, attachment.getData());
            return null;
        });
    }

    @DeleteMapping("/{competitionId}/{filename}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> deleteAttachment(@PathVariable String competitionId, @PathVariable String filename) {
        return commandGateway.send(new RemoveAttachment(competitionId, filename));
    }


    @GetMapping("/{competitionId}/")
    Future<List<AttachmentDto>> findAttachments(@PathVariable String competitionId) {
        return gateway.query(new AttachmentsPerCompetitionQuery(competitionId), new MultipleInstancesResponseType<>(AttachmentDto.class));

    }

    @GetMapping("/{competitionId}/{filename}")
    Future<AttachmentDto> findAttachments(@PathVariable String competitionId, @PathVariable String filename) {
        return gateway.query(new AttachmentFilenameQuery(competitionId, filename), new InstanceResponseType<>(AttachmentDto.class));
    }
}
