package org.lisasp.competitionstorage.rest;

import lombok.RequiredArgsConstructor;
import org.lisasp.competitionstorage.logic.query.attachment.AttachmentDataStorage;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/downloads", produces = "application/x-download")
@RequiredArgsConstructor
public class Downloads {

    private final AttachmentDataStorage projection;

    @GetMapping("/{competitionId}/attachments/{filename}")
    byte[] fetchAttachmentData(@PathVariable String competitionId, @PathVariable String filename) {
        return projection.fetchAttachmentData(competitionId, filename);
    }
}
