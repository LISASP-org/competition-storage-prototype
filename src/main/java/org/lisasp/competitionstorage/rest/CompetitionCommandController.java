package org.lisasp.competitionstorage.rest;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.lisasp.competitionstorage.dto.AddAttachmentDto;
import org.lisasp.competitionstorage.dto.RegisterCompetitionDto;
import org.lisasp.competitionstorage.dto.UpdateAttachmentDto;
import org.lisasp.competitionstorage.dto.UpdateCompetitionDto;
import org.lisasp.competitionstorage.logic.command.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.concurrent.Future;

@RestController()
@RequestMapping("/api/competitions")
@RequiredArgsConstructor
public class CompetitionCommandController {

    private final IdentifierFactory identifierFactory = IdentifierFactory.getInstance();

    private final CommandGateway commandGateway;

    @PostMapping("")
    Future<String> register(Principal principal, @RequestBody RegisterCompetitionDto dto) {
        return commandGateway.send(new RegisterCompetition(identifierFactory.generateIdentifier(), dto.getShortName()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> update(@RequestBody UpdateCompetitionDto competition, @PathVariable String id) {
        return commandGateway.sendAndWait(new UpdateCompetitionProperties(id, competition.getName(), competition.getStartDate(), competition.getEndDate(), competition.getLocation(), competition.getCountry(), competition.getOrganization(), competition.getDescription()));
    }

    @PostMapping("/{id}/close")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> close(@PathVariable String id) {
        return commandGateway.sendAndWait(new CloseCompetition(id));
    }

    @PostMapping("/{id}/finalize")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> finalize(@PathVariable String id) {
        return commandGateway.send(new FinalizeCompetition(id));
    }

    @PostMapping("/{id}/reopen")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> reopen(@PathVariable String id) {
        return commandGateway.send(new ReopenCompetition(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> delete(@PathVariable String id) {
        return commandGateway.send(new RevokeCompetition(id));
    }

    @PostMapping("/{id}/attachments")
    Future<Void> addAttachment(@RequestBody AddAttachmentDto attachment, @PathVariable String id) {
        return commandGateway.send(new AddAttachment(id, attachment.getFilename()));
    }

    @PutMapping("/{id}/attachments/{filename}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> updateAttachment(@RequestBody UpdateAttachmentDto attachment, @PathVariable String id, @PathVariable String filename) {
        return commandGateway.send(new UpdateAttachment(id, filename, attachment.getData()));
    }

    @DeleteMapping("/{id}/attachments/{filename}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> deleteAttachment(@PathVariable String id, @PathVariable String filename) {
        return commandGateway.send(new RemoveAttachment(id, filename));
    }
}
