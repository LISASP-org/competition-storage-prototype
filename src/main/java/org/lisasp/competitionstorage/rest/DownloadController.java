package org.lisasp.competitionstorage.rest;

import org.lisasp.competitionstorage.dto.AttachmentDto;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.lisasp.competitionstorage.logic.Competitions;
import org.lisasp.competitionstorage.logic.exception.AttachmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/downloads", produces = "application/pdf")
public class DownloadController {

    private final Competitions competitions;

    @Autowired
    DownloadController(Competitions competitions) {
        this.competitions = competitions;
    }

    @GetMapping("")
    List<CompetitionDto> find(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
        if ("".equals(name)) {
            return competitions.findAll();
        }
        return competitions.findByName(name);
    }

    @GetMapping("/{id}/attachments/{attachmentId}/{name}")
    byte[] findAttachments(@PathVariable String id, @PathVariable String attachmentId, @PathVariable String name) {
        AttachmentDto attachment = competitions.findAttachment(id, attachmentId);
        if (!attachment.getName().equalsIgnoreCase(name.trim())) {
            throw new AttachmentNotFoundException(id, attachmentId, name);
        }
        return attachment.getData();
    }
}
