package org.lisasp.competitionstorage;

import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.competitionstorage.logic.api.*;
import org.lisasp.competitionstorage.logic.exception.CompetitionStatusException;
import org.lisasp.competitionstorage.logic.model.Competition;

import java.time.LocalDate;

public class CompetitionTests {

    private AggregateTestFixture<Competition> testFixture;

    @BeforeEach
    void setUp() {
        testFixture = new AggregateTestFixture<>(Competition.class);
    }

    @Test
    void testRegisterCompetition() {
        testFixture.givenNoPriorActivity()
                .when(new RegisterCompetition("competitionId", "shortname"))
                .expectEvents(new CompetitionRegistered("competitionId", "shortname"));
    }

    @Test
    void testUpdateCompetitionProperties() {
        testFixture.given(new CompetitionRegistered("competitionId", "shortname"))
                .when(new UpdateCompetitionProperties("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .expectEvents(new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"));
    }

    @Test
    void testUpdateCompetitionProperties_Nothing() {
        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .when(new UpdateCompetitionProperties("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .expectNoEvents();
    }

    @Test
    void testUpdateCompetitionProperties_One() {
        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "-", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .when(new UpdateCompetitionProperties("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .expectEvents(new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"));

        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 2), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .when(new UpdateCompetitionProperties("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .expectEvents(new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"));

        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 3), "Location", "ZZ", "Organization", "Description"))
                .when(new UpdateCompetitionProperties("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .expectEvents(new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"));

        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "-", "ZZ", "Organization", "Description"))
                .when(new UpdateCompetitionProperties("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .expectEvents(new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"));

        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "-", "Organization", "Description"))
                .when(new UpdateCompetitionProperties("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .expectEvents(new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"));

        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "-", "Description"))
                .when(new UpdateCompetitionProperties("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .expectEvents(new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"));

        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "-"))
                .when(new UpdateCompetitionProperties("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .expectEvents(new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"));
    }

    @Test
    void testRevokeCompetition() {
        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .when(new RevokeCompetition("competitionId"))
                .expectEvents(new CompetitionRevoked("competitionId"));
    }

    @Test
    void testCloseCompetition() {
        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .when(new CloseCompetition("competitionId"))
                .expectEvents(new CompetitionClosed("competitionId"));
    }

    @Test
    void testFinalizeCompetition() {
        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"),
                new CompetitionClosed("competitionId"))
                .when(new FinalizeCompetition("competitionId"))
                .expectEvents(new CompetitionFinalized("competitionId"));
    }

    @Test
    void testFinalizeCompetition_OnOpenCompetition() {
        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"))
                .when(new FinalizeCompetition("competitionId"))
                .expectException(CompetitionStatusException.class);
    }

    @Test
    void testReopenCompetition() {
        testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"),
                new CompetitionClosed("competitionId"))
                .when(new ReopenCompetition("competitionId"))
                .expectEvents(new CompetitionReopened("competitionId"));
    }
}
