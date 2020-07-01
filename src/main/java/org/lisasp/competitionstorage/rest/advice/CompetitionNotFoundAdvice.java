package org.lisasp.competitionstorage.rest.advice;

import org.lisasp.competitionstorage.logic.exception.CompetitionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CompetitionNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CompetitionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(CompetitionNotFoundException ex) {
        return ex.getMessage();
    }
}
