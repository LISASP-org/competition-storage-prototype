package org.lisasp.competitionstorage.rest.advice;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.queryhandling.NoHandlerForQueryException;
import org.lisasp.competitionstorage.logic.exception.*;
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

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CompetitionNotFoundException.class)
    String competitionNotFoundHandler(CompetitionNotFoundException ex) {
        log.warn(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(CompetitionStatusException.class)
    String competitionStatusHandler(CompetitionStatusException ex) {
        log.warn(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(CouldNotSerializeException.class)
    String couldNotSerializeHandler(CouldNotSerializeException ex) {
        log.warn(ex.getMessage(), ex);

        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DataMissingException.class)
    String idMissingHandler(DataMissingException ex) {
        log.warn(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DtoNotSpecifiedException.class)
    String dtoNotSpecifiedHandler(DtoNotSpecifiedException ex) {
        log.warn(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdMissingException.class)
    String idMissingHandler(IdMissingException ex) {
        log.warn(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IdsNotValidException.class)
    String idsNotValidHandler(IdsNotValidException ex) {
        log.warn(ex.getMessage(), ex);
        return ex.getMessage();
    }

}
