package org.lisasp.competitionstorage.logic.competition;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.lisasp.competitionstorage.logic.exception.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Competition {

    @AggregateIdentifier
    private String id;

    @NotNull
    private String shortName;

    private CompetitionStatus status = CompetitionStatus.Open;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private String location;

    private String country;

    private String organization;

    private String description;

    private final List<Attachment> attachments = new ArrayList<>();

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
        assertStatus(CompetitionStatus.Open);

        if (hasChanges(command)) {
            apply(new CompetitionPropertiesUpdated(command.getId(), command.getName(), command.getStartDate(), command.getEndDate(), command.getLocation(), command.getCountry(), command.getOrganization(), command.getDescription()));
        }
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

    private void assertStatus(CompetitionStatus... expected) {
        for (CompetitionStatus cs : expected) {
            if (status == cs) {
                return;
            }
        }
        throw new CompetitionStatusException(expected, status);
    }

    @CommandHandler
    public String addAttachment(AddAttachment command) {
        Optional<Attachment> attachment = getAttachment(command.getFilename(), false);
        if (attachment.isPresent()) {
            return attachment.get().getId();
        }
        apply(new AttachmentAdded(command.getCompeitionId(), command.getAttachmentId(), command.getFilename()));
        return command.getAttachmentId();
    }

    @EventHandler
    public void on(AttachmentAdded event) {
        getAttachment(event.getFilename(), true).get().on(event);
    }

    @CommandHandler
    public void updateAttachment(UploadAttachment command) {
        assertAttachmentExists(command.getFilename());
        getAttachment(command.getFilename(), false).get().upload(command);
    }

    @EventHandler
    public void on(AttachmentUploaded event) {
        getAttachment(event.getFilename(), true).get().on(event);
    }

    @CommandHandler
    public void revokeAttachment(RemoveAttachment command) {
        if (getAttachment(command.getFilename(), false).isPresent()) {
            getAttachment(command.getFilename(),false).get().revoke(command);
        }
    }

    @EventHandler
    public void on(AttachmentRemoved event) {
        attachments.removeIf(a -> a.matchesFilename(event.getFilename()));
    }

    private void assertAttachmentExists(String filename) {
        if (getAttachment(filename,false).isEmpty()) {
            throw new AttachmentNotFoundException(this.id, filename);
        }
    }

    private Optional<Attachment> getAttachment(String filename, boolean createIfNotFound) {
        Optional<Attachment> attachment = attachments.stream().filter(a -> a.matchesFilename(filename)).findFirst();
        if (attachment.isEmpty() && createIfNotFound) {
            attachment = Optional.of(new Attachment(filename));
            attachments.add(attachment.get());
        }
        return attachment;
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
        return isDifferent(description, command.getDescription());
    }

    private boolean isDifferent(Object a, Object b) {
        if (a == null) {
            return true;
        }
        return !a.equals(b);
    }
}
