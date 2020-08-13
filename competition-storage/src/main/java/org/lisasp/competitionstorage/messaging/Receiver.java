package org.lisasp.competitionstorage.messaging;

import lombok.extern.slf4j.Slf4j;
import org.lisasp.messages.ResultsMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Receiver {

  // @JmsListener(destination = "${competition-storage.queue}")
  public void receive(ResultsMessage message) {
    log.info("received message (ResultsMessage)='{}'", message);
  }

  // @JmsListener(destination = "${competition-storage.queue}", )
  public void receive(String message) {
    log.info("received message (String)='{}'", message);
  }
}
