package org.lisasp.competitionstorage.rest;

import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.MultipleInstancesResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.lisasp.competitionstorage.dto.AttachmentDto;
import org.lisasp.competitionstorage.logic.query.competition.AllCompetitionsQuery;
import org.lisasp.competitionstorage.logic.query.competition.CompetitionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Future;

@RestController()
@RequestMapping("/api/competitions")
@RequiredArgsConstructor
public class CompetitionQueryController {

    private final QueryGateway gateway;

    @GetMapping("")
    Future<List<CompetitionDto>> find() {
        return gateway.query(new AllCompetitionsQuery(), new MultipleInstancesResponseType<>(CompetitionDto.class));
    }

    @GetMapping("/{id}")
    CompetitionDto findById(@PathVariable String id) {
        return null;
    }

    @GetMapping("/{id}/attachments")
    List<AttachmentDto> findAttachments(@PathVariable String id) {
        return null;
    }

    @GetMapping("/{id}/attachments/{attachmentId}")
    AttachmentDto findAttachments(@PathVariable String id, @PathVariable String attachmentId) {
        return null;
    }
}
