package org.lisasp.competitionstorage.rest;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.messaging.responsetypes.InstanceResponseType;
import org.axonframework.messaging.responsetypes.MultipleInstancesResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.lisasp.competitionstorage.dto.*;
import org.lisasp.competitionstorage.logic.api.*;
import org.lisasp.competitionstorage.logic.query.attachment.AttachmentQuery;
import org.lisasp.competitionstorage.logic.query.attachment.AttachmentsPerCompetitionQuery;
import org.lisasp.competitionstorage.logic.storage.AttachmentDataStorage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Future;

@RestController()
@RequestMapping("/api/competitions")
@RequiredArgsConstructor
public class Attachments {

    private final QueryGateway gateway;
    private final CommandGateway commandGateway;
    private final AttachmentDataStorage storage;

    @PostMapping("/{competitionId}/attachments")
    Future<Void> addAttachment(@RequestBody AddAttachmentDto attachment, @PathVariable String competitionId) {
        return commandGateway.send(new AddAttachment(competitionId, attachment.getFilename()));
    }

    @PutMapping("/{competitionId}/attachments/{filename}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> updateAttachment(@RequestBody UpdateAttachmentDto attachment, @PathVariable String competitionId, @PathVariable String filename) {
        commandGateway.send(new UpdateAttachment(competitionId, filename, attachment.getData()));
        return storage.storeAttachment(competitionId, filename, attachment.getData());
    }

    @DeleteMapping("/{competitionId}/attachments/{filename}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> deleteAttachment(@PathVariable String competitionId, @PathVariable String filename) {
        return commandGateway.send(new RemoveAttachment(competitionId, filename));
    }


    @GetMapping("/{competitionId}/attachments")
    Future<List<AttachmentDto>> findAttachments(@PathVariable String competitionId) {
        return gateway.query(new AttachmentsPerCompetitionQuery(competitionId), new MultipleInstancesResponseType<>(AttachmentDto.class));

    }

    @GetMapping("/{competitionId}/attachments/{filename}")
    Future<AttachmentDto> findAttachments(@PathVariable String competitionId, @PathVariable String filename) {
        return gateway.query(new AttachmentQuery(competitionId, filename), new InstanceResponseType<>(AttachmentDto.class));
    }
}
