package com.chessrating.api.controller;

import com.chessrating.exception.PlayerValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice(basePackages = "com.chessrating.api.controller")
public class PlayerControllerAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(PlayerControllerAdvice.class);

    @ExceptionHandler({PlayerValidationException.class})
    public ResponseEntity<String> playerValidationException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(prepareExceptionMessage(ex));
    }

    private String prepareExceptionMessage(Exception ex) {
        LOG.error("[PlayerControllerAdvice::prepareExceptionMessage] Failed to perform operation with Player. Cause:", ex);
        return Objects.isNull(ex.getMessage()) ?
                "Failed to perform operation with Player. Please check logs for details"
                : ex.getMessage();
    }
}
