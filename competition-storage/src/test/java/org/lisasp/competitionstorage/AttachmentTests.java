package org.lisasp.competitionstorage;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.TestExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.competitionstorage.logic.competition.*;

import java.time.LocalDate;

public class AttachmentTests {

    private AggregateTestFixture<Competition> testFixture;
    private TestExecutor<Competition> executor;

    @BeforeEach
    void setUp() {
        testFixture = new AggregateTestFixture<>(Competition.class);
        executor = testFixture.given(new CompetitionRegistered("competitionId", "shortname"),
                new CompetitionPropertiesUpdated("competitionId", "Name", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), "Location", "ZZ", "Organization", "Description"));
    }

    @Test
    void testAddAttachment() {
        executor.when(new AddAttachment("competitionId", "attachmentId", "file.pdf"))
                .expectEvents(new AttachmentAdded("competitionId", "attachmentId", "file.pdf"));
    }

    @Test
    void testUpdateAttachment() {
        executor.andGiven(new AttachmentAdded("competitionId", "attachmentId", "file.pdf"))
                .when(new UploadAttachment("competitionId", "file.pdf", new byte[13]))
                .expectEvents(new AttachmentUploaded("competitionId", "file.pdf"));
    }

    @Test
    void testRemoveAttachment() {
        executor.andGiven(new AttachmentAdded("competitionId", "attachmentId", "file.pdf"))
                .when(new RemoveAttachment("competitionId", "file.pdf"))
                .expectEvents(new AttachmentRemoved("competitionId", "attachmentId","file.pdf"));
    }
}
