package org.lisasp.attachmentstorage.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.messages.ResultsDto;
import org.lisasp.messages.ResultsMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
@Slf4j
@RequiredArgsConstructor
public class ResultMessageConverter implements MessageConverter {

    private final ObjectMapper mapper;

    @Override
    public Message toMessage(Object o, Session session) throws JMSException, MessageConversionException {
        ResultsMessage person = (ResultsMessage) o;
        String payload = null;
        try {
            payload = mapper.writeValueAsString(person);
            log.info("outbound json='{}'", payload);
        } catch (JsonProcessingException e) {
            log.error("error converting form person", e);
        }

        TextMessage message = session.createTextMessage();
        message.setText(payload);

        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        TextMessage textMessage = (TextMessage) message;
        String payload = textMessage.getText();
        log.info("inbound json='{}'", payload);

        ResultsMessage person = null;
        try {
            person = mapper.readValue(payload, ResultsMessage.class);
        } catch (Exception e) {
            log.error("error converting to person", e);
        }

        return person;
    }
}
