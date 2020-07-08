package org.lisasp.competitionstorage.dto

import lombok.EqualsAndHashCode
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.*

data class RegisterCompetitionDto(@NotBlank @Min(4) @Max(12) val shortName: String)

data class UpdateCompetitionDto(@NotBlank @Min(8) @Max(100) val name: String,
                                @NotNull val startDate: LocalDate,
                                @NotNull val endDate: LocalDate,
                                @NotBlank val location: String,
                                @NotBlank val country: String,
                                @NotBlank val organization: String,
                                val description: String)

data class AddAttachmentDto(@NotBlank val filename: String)

@EqualsAndHashCode
data class UpdateAttachmentDto(@NotNull val data: ByteArray)

data class AttachmentDto(val id: String, val filename: String, var data: ByteArray)
