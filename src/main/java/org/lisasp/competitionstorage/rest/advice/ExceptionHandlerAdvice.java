package org.lisasp.competitionstorage.rest.advice;

import org.axonframework.modelling.command.AggregateNotFoundException;
import org.lisasp.competitionstorage.logic.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AggregateNotFoundException.class)
    String aggregateNotFoundHandler(AggregateNotFoundException ex) {
        return String.format("Competition \"%s\" not found.", ex.getAggregateIdentifier());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(CompetitionStatusException.class)
    String competitionStatusHandler(CompetitionStatusException ex) {
        return ex.getMessage();
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdMissingException.class)
    String idMissingHandler(IdMissingException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IdsNotValidException.class)
    String idsNotValidHandler(IdsNotValidException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidationException.class)
    String validationHandler(ValidationException ex) {
        return ex.getMessage();
    }
}
