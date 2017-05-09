package com.beerjournal.infrastructure.error;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private final SlackErrorSender slackErrorSender;

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
        logger.error("Error", ex);
        slackErrorSender.sendErrorMsg(ex.toString());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}