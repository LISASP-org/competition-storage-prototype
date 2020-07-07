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
import java.util.Map;

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

    private Map<String, Asset> assets = new HashMap<>();

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
        initialize();
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
    public void apply(AddAsset command) {
        AggregateLifecycle.apply(new AssetAdded(command.getId(), command.getAssetId(), command.getFilename(), command.getData()));
    }

    @EventSourcingHandler
    public void on(AssetAdded event) {
        assets.put(event.getAssetId(), new Asset(event));
    }

    @CommandHandler
    public void apply(UpdateAsset command) {
        assertAssetExists(command.getAssetId());
        AggregateLifecycle.apply(new AssetUpdated(command.getId(), command.getAssetId(), command.getData()));
    }

    @EventSourcingHandler
    public void on(AssetUpdated event) {
        getAsset(event.getAssetId()).on(event);
    }

    @CommandHandler
    public void apply(RemoveAsset command) {
        if (assets.containsKey(command.getAssetId())) {
            AggregateLifecycle.apply(new AssetRemoved(command.getId(), command.getAssetId()));
        }
    }

    @EventSourcingHandler
    public void on(AssetRemoved event) {
        assets.remove(event.getAssetId());
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

    private void assertAssetExists(String assetId) {
        if (!assets.containsKey(assetId)) {
            throw new AssetNotFoundException(this.id, assetId);
        }
    }

    private void initialize() {
        if (assets == null) {
            assets = new HashMap<>();
        }
        if (results == null) {
            results = new HashMap<>();
        }
    }

    private Asset getAsset(String assetId) {
        Asset asset = assets.get(assetId);
        if (asset == null) {
            throw new AssetNotFoundException(getId(), assetId);
        }
        return asset;
    }
}
