package org.lisasp.competitionstorage.rest.advice;

import org.lisasp.competitionstorage.logic.exception.IdsNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DataInconsistencyAdvice {

    @ResponseBody
    @ExceptionHandler(IdsNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String employeeNotFoundHandler(IdsNotValidException ex) {
        return ex.getMessage();
    }
}
