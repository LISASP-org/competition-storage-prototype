package org.lisasp.competitionstorage.logic.query.attachment

data class AttachmentsPerCompetitionQuery(val competitionId: String)
data class AttachmentQuery(val competitionId: String, val filename: String)

