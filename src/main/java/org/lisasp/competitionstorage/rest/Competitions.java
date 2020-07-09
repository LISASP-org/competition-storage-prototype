package org.lisasp.competitionstorage.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.messaging.responsetypes.InstanceResponseType;
import org.axonframework.messaging.responsetypes.MultipleInstancesResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.lisasp.competitionstorage.dto.*;
import org.lisasp.competitionstorage.logic.api.*;
import org.lisasp.competitionstorage.logic.query.competition.AllCompetitionsQuery;
import org.lisasp.competitionstorage.logic.query.competition.CompetitionEntity;
import org.lisasp.competitionstorage.logic.query.competition.CompetitionQuery;
import org.lisasp.competitionstorage.logic.storage.AttachmentDataStorage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.Future;

@RestController()
@RequestMapping("/api/competitions")
@RequiredArgsConstructor
public class Competitions {

    private final IdentifierFactory identifierFactory = IdentifierFactory.getInstance();
    private final QueryGateway gateway;
    private final CommandGateway commandGateway;

    @PostMapping("")
    Future<String> register(Principal principal, @RequestBody RegisterCompetitionDto dto) {
        return commandGateway.send(new RegisterCompetition(identifierFactory.generateIdentifier(), dto.getShortName()));
    }

    @PutMapping("/{competitionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> update(@RequestBody UpdateCompetitionDto competition, @PathVariable String competitionId) {
        return commandGateway.sendAndWait(new UpdateCompetitionProperties(competitionId, competition.getName(), competition.getStartDate(), competition.getEndDate(), competition.getLocation(), competition.getCountry(), competition.getOrganization(), competition.getDescription()));
    }

    @PostMapping("/{competitionId}/close")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> close(@PathVariable String competitionId) {
        return commandGateway.sendAndWait(new CloseCompetition(competitionId));
    }

    @PostMapping("/{competitionId}/finalize")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> finalize(@PathVariable String competitionId) {
        return commandGateway.send(new FinalizeCompetition(competitionId));
    }

    @PostMapping("/{competitionId}/reopen")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> reopen(@PathVariable String competitionId) {
        return commandGateway.send(new ReopenCompetition(competitionId));
    }

    @DeleteMapping("/{competitionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    Future<Void> delete(@PathVariable String competitionId) {
        return commandGateway.send(new RevokeCompetition(competitionId));
    }


    @Operation(summary = "Get all competitions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched the list of competitions",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CompetitionEntity.class))) })})
    @GetMapping("")
    Future<List<CompetitionEntity>> find() {
        return gateway.query(new AllCompetitionsQuery(), new MultipleInstancesResponseType<>(CompetitionEntity.class));
    }

    @Operation(summary = "Get competition by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the competition",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompetitionEntity.class)) }),
            @ApiResponse(responseCode = "404", description = "Competition not found",
                    content = @Content) })
    @GetMapping("/{competitionId}")
    Future<CompetitionEntity> findById(@PathVariable String competitionId) {
        return gateway.query(new CompetitionQuery(competitionId), new InstanceResponseType<>(CompetitionEntity.class));
    }
}
