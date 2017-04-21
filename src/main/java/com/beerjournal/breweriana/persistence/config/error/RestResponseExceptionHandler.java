package com.beerjournal.breweriana.persistence.config.error;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ClientErrorException.class})
    public ResponseEntity<ErrorInfo> handleClientErrorException(ClientErrorException ex) {
        return new ResponseEntity<>(ex.getInfo(), ex.getInfo().getStatus());
    }

    @ExceptionHandler({ObjectOptimisticLockingFailureException.class, OptimisticLockingFailureException.class,
            DataIntegrityViolationException.class})
    public ResponseEntity<ErrorInfo> handleConflict(Exception ex) {
        return new ResponseEntity<>(ErrorInfo.CONFLICT, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleOtherException(Exception ex) {
        if (ex instanceof ClientErrorException) {
            return handleClientErrorException((ClientErrorException) ex);
        } else if (ex instanceof ObjectOptimisticLockingFailureException || ex instanceof OptimisticLockingFailureException
                || ex instanceof DataIntegrityViolationException) {
            return handleConflict(ex);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}