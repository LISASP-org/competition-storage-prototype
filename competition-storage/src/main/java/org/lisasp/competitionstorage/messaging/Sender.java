package org.lisasp.competitionstorage.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.messages.ResultsMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Sender {

  @Value("${competition-storage.queue}")
  private String queuename;

  private final JmsTemplate jmsTemplate;

  public void send(ResultsMessage message) {
    log.info("send message='{}'", message);
    jmsTemplate.convertAndSend(queuename, message);
  }
}
