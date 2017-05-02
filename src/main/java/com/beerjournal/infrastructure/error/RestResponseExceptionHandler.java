package com.beerjournal.infrastructure.error;


import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BeerJournalException.class})
    public ResponseEntity<ErrorInfo> handleBeerJournalException(BeerJournalException bjException) {
        ErrorInfo errorInfo = bjException.getInfo();
        return new ResponseEntity<>(errorInfo, errorInfo.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleOtherException(Exception ex) {
        if (ex instanceof BeerJournalException) {
            return handleBeerJournalException((BeerJournalException) ex);
        }
        logger.error(ex.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}