package org.lisasp.messages

import lombok.EqualsAndHashCode
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

data class AddAttachmentDto(@NotBlank val competitionId:String, @NotBlank val filename: String)

data class UploadAttachmentDto(@NotNull val data: ByteArray)

data class AttachmentDto(@NotBlank val competitionId: String, @NotBlank val filename: String)

enum class SexIndividual { MALE, FEMALE }
enum class SexTeam { MALE, FEMALE, MIXED }
enum class CompetitionType { POOL, OPENWATER }

data class Individual(@NotBlank val firstName: String, @NotBlank val lastName: String, @NotBlank val sex: SexIndividual, @Min(0) val yearOfBirth: Int, @NotBlank val agegroup: String, @NotNull val results: List<Result>);
data class Team(@NotBlank val name: String, @NotNull val members: List<Individual>, @NotBlank val sex: SexTeam, @NotBlank val agegroup: String, @NotNull val results: List<Result>);
data class Result(@NotBlank val discipline: String, @Min(0) val timeInMilliseconds: Long, @NotNull val penalty: String, val event: Int, val heat: Int, val lane: Int, val round: Int)
data class ResultsDto(@NotNull val type: CompetitionType, @NotNull val individuals: List<Individual>, @NotNull val teams: List<Team>)

enum class MessageType { JSON, PDF }
data class ResultsMessage(@NotBlank val competitionId: String, @NotBlank val filename: String, val type: MessageType, @NotNull val results: ByteArray)
