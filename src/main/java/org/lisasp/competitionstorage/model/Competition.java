package org.lisasp.competitionstorage.model;

import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.lisasp.competitionstorage.logic.command.*;
import org.lisasp.competitionstorage.logic.event.*;
import org.lisasp.competitionstorage.logic.exception.*;
import org.lisasp.competitionstorage.model.util.CompetitionStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Aggregate
public class Competition {

    @AggregateIdentifier
    @Getter
    private String id;

    @Getter
    private CompetitionStatus status = CompetitionStatus.Open;

    @Getter
    @NotNull
    private String name;

    @Getter
    @NotNull
    private String shortName;

    @Getter
    @NotNull
    private LocalDate startDate;

    @Getter
    private LocalDate endDate;

    @Getter
    private String location;

    @Getter
    private String country;

    @Getter
    private String organization;

    @Getter
    private String description;

    private Set<String> attachments = new HashSet<>();

    private Map<String, Result> results = new HashMap<>();

    public Competition() {
    }

    @CommandHandler
    public Competition(RegisterCompetition command) {
        assertNew();
        AggregateLifecycle.apply(new CompetitionRegistered(command.getId(), command.getShortName()));
    }

    @EventSourcingHandler
    public void on(CompetitionRegistered event) {
        this.id = event.getId();
        this.shortName = event.getShortName();
    }

    @CommandHandler
    public void handle(UpdateCompetitionProperties command) {
        assertId(command.getId());
        assertOpen();
        AggregateLifecycle.apply(new CompetitionPropertiesUpdated(command.getId(), command.getName(), command.getStartDate(), command.getEndDate(), command.getLocation(), command.getCountry(), command.getOrganization(), command.getDescription()));
    }

    @EventSourcingHandler
    public void on(CompetitionPropertiesUpdated event) {
        name = event.getName();
        startDate = event.getStartDate();
        endDate = event.getEndDate();
        location = event.getLocation();
        country = event.getCountry();
        organization = event.getOrganization();
        description = event.getDescription();
    }

    @CommandHandler
    public void apply(AddAttachment command) {
        AggregateLifecycle.apply(new AttachmentAdded(command.getId(), command.getFilename()));
    }

    @EventSourcingHandler
    public void on(AttachmentAdded event) {
        attachments.add(event.getFilename());
    }

    @CommandHandler
    public void apply(RemoveAttachment command) {
        if (attachments.contains(command.getFilename())) {
            AggregateLifecycle.apply(new AttachmentRemoved(command.getId(), command.getFilename()));
        }
    }

    @EventSourcingHandler
    public void on(AttachmentRemoved event) {
        attachments.remove(event.getFilename());
    }

    private void assertNew() {
        if (id != null && !id.trim().isEmpty()) {
            throw new CompetitionAlreadyExistsException(id);
        }
    }

    private void assertId(String commandId) {
        if (id == null || id.trim().isEmpty()) {
            throw new IdMissingException();
        }
        if (!id.equals(commandId)) {
            throw new IdsNotValidException(id, commandId);
        }
    }

    private void assertOpen() {
        if (status != CompetitionStatus.Open) {
            throw new CompetitionStatusException(CompetitionStatus.Open, status);
        }
    }
}
