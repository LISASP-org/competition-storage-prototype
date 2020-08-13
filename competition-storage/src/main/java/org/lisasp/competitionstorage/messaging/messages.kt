package org.lisasp.competitionstorage.messaging

import javax.validation.constraints.*

data class AttachmentAdded(@NotEmpty val competitionId: String,@NotEmpty val attachmentId: String, @NotEmpty val filename: String)
data class AttachmentUploaded(@NotEmpty val competitionId: String, @NotEmpty val filename: String)
data class AttachmentRemoved(@NotEmpty val competitionId: String, @NotEmpty val attachmentId:String, @NotEmpty val filename: String)
