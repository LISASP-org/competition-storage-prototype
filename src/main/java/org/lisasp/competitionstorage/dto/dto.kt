package org.lisasp.competitionstorage.dto

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDate
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class RegisterCompetitionDto(@NotBlank @Min(4) @Max(12) val shortName: String)

data class UpdateCompetitionDto(@TargetAggregateIdentifier @NotBlank @Min(16) val id: String,
                                       @NotBlank @Min(8) @Max(100) val name: String,
                                       @NotNull val startDate: LocalDate,
                                       @NotNull val endDate: LocalDate,
                                       @NotBlank val location: String,
                                       @NotBlank val country: String,
                                       @NotBlank val organization: String,
                                       val description: String)

data class AddAssetDto(@NotBlank val filename: String, @NotNull val data: ByteArray)
data class UpdateAssetDto(@NotNull val data: ByteArray)

data class IdDto(@NotBlank @Min(16) val id: String)
