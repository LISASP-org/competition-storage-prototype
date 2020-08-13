package org.lisasp.attachmentstorage.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.attachmentstorage.storage.AttachmentDataStorage;
import org.lisasp.messages.ResultsDto;
import org.lisasp.messages.ResultsMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class Receiver {

  private final AttachmentDataStorage storage;

  private final ObjectMapper mapper;

  @JmsListener(destination = "${competition-storage.queue}")
  public void receive(ResultsMessage message) throws IOException {
    log.info("received message (ResultsMessage)='{}'", message.toString().substring(0, 50));
    switch (message.getType()) {
      case JSON:
        mapper.readValue(message.getResults(), ResultsDto.class);
        break;
      default:
        // Nothing to do;
    }
    storage.storeAttachment(message.getCompetitionId(), message.getFilename(), message.getResults());
  }
}
