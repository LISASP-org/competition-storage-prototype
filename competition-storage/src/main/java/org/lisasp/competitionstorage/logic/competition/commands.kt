package org.lisasp.competitionstorage.logic.competition

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.bson.types.Binary
import java.time.LocalDate
import javax.validation.constraints.*

data class RegisterCompetition(@TargetAggregateIdentifier @NotBlank @Min(16) val id: String,
                               @NotBlank val shortName: String)

data class UpdateCompetitionProperties(@TargetAggregateIdentifier @NotBlank @Min(16) val id: String,
                                       @NotBlank @Min(8) @Max(100) val name: String,
                                       @NotNull val startDate: LocalDate,
                                       @NotNull val endDate: LocalDate,
                                       @NotBlank val location: String,
                                       @NotBlank val country: String,
                                       @NotBlank val organization: String,
                                       val description: String)

data class CloseCompetition(@TargetAggregateIdentifier @NotBlank @Min(16) val id: String)
data class FinalizeCompetition(@TargetAggregateIdentifier @NotBlank @Min(16) val id: String)
data class ReopenCompetition(@TargetAggregateIdentifier @NotBlank @Min(16) val id: String)
data class RevokeCompetition(@TargetAggregateIdentifier @NotBlank @Min(16) val id: String)

data class AddAttachment(@TargetAggregateIdentifier @NotBlank @Min(16) val compeitionId: String, @NotBlank @Min(16) val attachmentId: String, @NotBlank val filename: String)
data class UploadAttachment(@TargetAggregateIdentifier @NotBlank @Min(16) val compeitionId: String, @NotBlank val filename: String, val data : ByteArray)
data class RemoveAttachment(@TargetAggregateIdentifier @NotBlank @Min(16) val competitionId: String, @NotBlank val filename: String)
