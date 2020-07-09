package org.lisasp.competitionstorage.rest;

import lombok.RequiredArgsConstructor;
import org.lisasp.competitionstorage.logic.storage.AttachmentDataStorage;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.Charset;

@RestController()
@RequestMapping(value = "/downloads", produces = "application/pdf")
@RequiredArgsConstructor
public class Downloads {

    private final AttachmentDataStorage projection;

    @GetMapping("/{competitionId}/attachments/{filename}")
    byte[] fetchAttachmentData(@PathVariable String competitionId, @PathVariable String filename) {
        return projection.fetchAttachmentData(competitionId, filename);
    }
}
