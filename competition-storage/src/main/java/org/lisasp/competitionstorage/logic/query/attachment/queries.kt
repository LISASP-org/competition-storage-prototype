package org.lisasp.competitionstorage.logic.query.attachment

data class AttachmentsPerCompetitionQuery(val competitionId: String)
data class AttachmentFilenameQuery(val competitionId: String, val filename: String)
data class AttachmentIdQuery(val attachmentId: String)

