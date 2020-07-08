package org.lisasp.competitionstorage.rest;

import org.lisasp.competitionstorage.logic.query.competition.CompetitionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/downloads", produces = "application/pdf")
public class DownloadController {

    @Autowired
    DownloadController() {

    }

    @GetMapping("")
    List<CompetitionDto> find(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
        return null;
    }

    @GetMapping("/{id}/attachments/{attachmentId}/{name}")
    byte[] findAttachments(@PathVariable String id, @PathVariable String attachmentId, @PathVariable String name) {
        return null;
    }
}
