package org.lisasp.competitionstorage.logic.event

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

data class CompetitionAccepted(val id: String)
data class CompetitionFinalized(val id: String)
data class CompetitionReopened(val id: String)
data class CompetitionRevoked(val id: String)

data class AttachmentAdded(val id: String, val attachmentId: String, val filename: String, val data: ByteArray)
data class AttachmentUpdated(val id: String, val attachmentId: String, val data: ByteArray)
data class AttachmentRemoved(val id: String, val attachmentId: String)
