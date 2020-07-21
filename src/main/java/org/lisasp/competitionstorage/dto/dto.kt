package org.lisasp.competitionstorage.dto

import lombok.EqualsAndHashCode
import org.bson.types.Binary
import java.time.LocalDate
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class RegisterCompetitionDto(@NotBlank @Min(4) @Max(12) val shortName: String)

data class UpdateCompetitionDto(@NotBlank @Min(8) @Max(100) val name: String,
                                @NotNull val startDate: LocalDate,
                                @NotNull val endDate: LocalDate,
                                @NotBlank val location: String,
                                @NotBlank val country: String,
                                @NotBlank val organization: String,
                                val description: String)

data class AddAttachmentDto(@NotBlank val filename: String)

data class UploadAttachmentDto(@NotNull val data: ByteArray)

data class AttachmentDto(@NotBlank val competitionId: String, @NotBlank val filename: String)
