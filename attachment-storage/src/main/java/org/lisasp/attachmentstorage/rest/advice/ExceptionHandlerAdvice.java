package org.lisasp.attachmentstorage.rest.advice;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.queryhandling.NoHandlerForQueryException;
import org.lisasp.attachmentstorage.exception.AttachmentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;
import java.util.concurrent.ExecutionException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AggregateNotFoundException.class)
    String aggregateNotFoundHandler(AggregateNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return String.format("Competition \"%s\" not found.", ex.getAggregateIdentifier());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidationException.class)
    String validationHandler(ValidationException ex) {
        log.warn(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InterruptedException.class)
    String InterruptedHandler(InterruptedException ex) {
        log.warn(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ExecutionException.class)
    String executionExceptionHandler(ExecutionException ex) {
        log.warn(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NoHandlerForQueryException.class)
    String noQueryHandlerFoundHandler(NoHandlerForQueryException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AttachmentNotFoundException.class)
    String attachmentNotFoundHandler(AttachmentNotFoundException ex) {
        log.warn(ex.getMessage(), ex);
        return ex.getMessage();
    }
}
