package org.lisasp.competitionstorage.logic.competition

import java.time.LocalDate

data class CompetitionRegistered(val id: String, val shortName: String)

data class CompetitionPropertiesUpdated(val id: String,
                                        val name: String,
                                        val startDate: LocalDate,
                                        val endDate: LocalDate,
                                        val location: String,
                                        val country: String,
                                        val organization: String,
                                        val description: String)

data class CompetitionClosed(val id: String)
data class CompetitionFinalized(val id: String)
data class CompetitionReopened(val id: String)
data class CompetitionRevoked(val id: String)

data class AttachmentAdded(val competitionId: String, val attachmentId: String, val filename: String)
data class AttachmentUploaded(val competitionId: String, val filename: String)
data class AttachmentRemoved(val competitionId: String, val attachmentId:String, val filename: String)
