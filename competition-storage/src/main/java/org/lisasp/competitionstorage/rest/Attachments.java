package org.lisasp.competitionstorage.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.messaging.responsetypes.InstanceResponseType;
import org.axonframework.messaging.responsetypes.MultipleInstancesResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.lisasp.competitionstorage.logic.competition.AddAttachment;
import org.lisasp.competitionstorage.logic.competition.RemoveAttachment;
import org.lisasp.competitionstorage.logic.competition.UploadAttachment;
import org.lisasp.competitionstorage.logic.exception.AttachmentNotFoundException;
import org.lisasp.competitionstorage.logic.exception.CouldNotSerializeException;
import org.lisasp.competitionstorage.logic.query.attachment.AttachmentFilenameQuery;
import org.lisasp.competitionstorage.logic.query.attachment.AttachmentIdQuery;
import org.lisasp.competitionstorage.logic.query.attachment.AttachmentsPerCompetitionQuery;
import org.lisasp.competitionstorage.messaging.ResultMessageConverter;
import org.lisasp.competitionstorage.messaging.Sender;
import org.lisasp.messages.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController()
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
@Slf4j
public class Attachments {

    private final QueryGateway gateway;
    private final CommandGateway commandGateway;

    private final Sender sender;

    private final ResultMessageConverter converter;

    private final IdentifierFactory identifierFactory = IdentifierFactory.getInstance();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @PostMapping("/")
    Future<String> addAttachment(@RequestBody AddAttachmentDto attachment) {
        return commandGateway.send(new AddAttachment(attachment.getCompetitionId(), identifierFactory.generateIdentifier(), attachment.getFilename()));
    }

    @PutMapping("/{attachmentId}/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> uploadJson(@RequestBody ResultsDto attachment, @PathVariable String attachmentId) {
        return executor.submit(() -> {
            byte[] data = toJSon(attachment);
            AttachmentDto entity = gateway.query(new AttachmentIdQuery(attachmentId), new InstanceResponseType<>(AttachmentDto.class)).get();
            assertAttachmentExists(attachmentId, entity);
            commandGateway.send(new UploadAttachment(entity.getCompetitionId(), entity.getFilename(), data));
            sender.send(new ResultsMessage(entity.getCompetitionId(), entity.getFilename(), MessageType.JSON, converter.toBytes(attachment)));
            return null;
        });
    }

    @PutMapping("/{attachmentId}/pdf")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> uploadPdf(@RequestBody UploadAttachmentDto attachment, @PathVariable String attachmentId) {
        return executor.submit(() -> {
            AttachmentDto entity = gateway.query(new AttachmentIdQuery(attachmentId), new InstanceResponseType<>(AttachmentDto.class)).get();
            assertAttachmentExists(attachmentId, entity);
            commandGateway.send(new UploadAttachment(entity.getCompetitionId(), entity.getFilename(), attachment.getData()));
            sender.send(new ResultsMessage(entity.getCompetitionId(), entity.getFilename(), MessageType.PDF, attachment.getData()));
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

    private void assertAttachmentExists(String attachmentId, AttachmentDto entity) {
        if (entity == null) {
            throw new AttachmentNotFoundException(attachmentId);
        }
    }

    private byte[] toJSon(ResultsDto results) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(bos, results);
            return bos.toByteArray();
        } catch (IOException ex) {
            throw new CouldNotSerializeException(ex);
        }
    }
}
