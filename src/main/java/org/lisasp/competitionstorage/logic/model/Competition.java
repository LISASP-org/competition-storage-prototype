package org.lisasp.competitionstorage.logic.model;

import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.lisasp.competitionstorage.logic.api.*;
import org.lisasp.competitionstorage.logic.exception.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

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

    public Competition() {
    }

    @CommandHandler
    public Competition(RegisterCompetition command) {
        apply(new CompetitionRegistered(command.getId(), command.getShortName()));
    }

    @EventSourcingHandler
    public void on(CompetitionRegistered event) {
        this.id = event.getId();
        this.shortName = event.getShortName();
    }

    @CommandHandler
    public void updateProperties(UpdateCompetitionProperties command) {
        assertId(command.getId());
        assertStatus(CompetitionStatus.Open);

        if (hasChanges(command)) {
            apply(new CompetitionPropertiesUpdated(command.getId(), command.getName(), command.getStartDate(), command.getEndDate(), command.getLocation(), command.getCountry(), command.getOrganization(), command.getDescription()));
        }
    }

    private boolean hasChanges(UpdateCompetitionProperties command) {
        if (isDifferent(name, command.getName())) {
            return true;
        }
        if (isDifferent(startDate, command.getStartDate())) {
            return true;
        }
        if (isDifferent(endDate, command.getEndDate())) {
            return true;
        }
        if (isDifferent(location, command.getLocation())) {
            return true;
        }
        if (isDifferent(country, command.getCountry())) {
            return true;
        }
        if (isDifferent(organization, command.getOrganization())) {
            return true;
        }
        if (isDifferent(description, command.getDescription())) {
            return true;
        }
        return false;
    }

    private boolean isDifferent(Object a, Object b) {
        if (a == null && b == null) {
            return false;
        }
        if (a == null || b == null) {
            return true;
        }
        return !a.equals(b);
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
    public void close(CloseCompetition command) {
        assertStatus(CompetitionStatus.Open, CompetitionStatus.Closed);
        if (status != CompetitionStatus.Closed) {
            apply(new CompetitionClosed(id));
        }
    }

    @EventHandler
    public void on(CompetitionClosed event) {
        status = CompetitionStatus.Closed;
    }

    @CommandHandler
    public void finalize(FinalizeCompetition command) {
        assertStatus(CompetitionStatus.Closed, CompetitionStatus.Finalized);
        if (status != CompetitionStatus.Finalized) {
            apply(new CompetitionFinalized(id));
        }
    }

    @EventHandler
    public void on(CompetitionFinalized event) {
        status = CompetitionStatus.Finalized;
    }

    @CommandHandler
    public void reopen(ReopenCompetition command) {
        assertStatus(CompetitionStatus.Open, CompetitionStatus.Closed, CompetitionStatus.Finalized);
        if (status != CompetitionStatus.Open) {
            apply(new CompetitionReopened(id));
        }
    }

    @EventHandler
    public void on(CompetitionReopened event) {
        status = CompetitionStatus.Open;
    }

    @CommandHandler
    public void revoke(RevokeCompetition command) {
        assertStatus(CompetitionStatus.Open, CompetitionStatus.Closed, CompetitionStatus.Revoked);
        if (status != CompetitionStatus.Revoked) {
            apply(new CompetitionRevoked(id));
        }
    }

    @EventHandler
    public void on(CompetitionRevoked event) {
        status = CompetitionStatus.Revoked;
    }

    @CommandHandler
    public void addAttachment(AddAttachment command) {
        apply(new AttachmentAdded(command.getId(), command.getFilename()));
    }

    @EventSourcingHandler
    public void on(AttachmentAdded event) {
        attachments.add(event.getFilename());
    }

    @CommandHandler
    public void removeAttachment(RemoveAttachment command) {
        if (attachments.contains(command.getFilename())) {
            apply(new AttachmentRemoved(command.getId(), command.getFilename()));
        }
    }

    @EventSourcingHandler
    public void on(AttachmentRemoved event) {
        attachments.remove(event.getFilename());
    }

    private void assertId(String commandId) {
        if (id == null || id.trim().isEmpty()) {
            throw new IdMissingException();
        }
        if (!id.equals(commandId)) {
            throw new IdsNotValidException(id, commandId);
        }
    }

    private void assertStatus(CompetitionStatus... expected) {
        for (CompetitionStatus cs : expected) {
            if (status == cs) {
                return;
            }
        }
        throw new CompetitionStatusException(expected, status);
    }
}
