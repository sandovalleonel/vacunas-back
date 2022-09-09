package com.init.inventariovacunaBACKEDN.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

public class MensajeBindinResult {
    public String verErrores(BindingResult bindingResult){
        return bindingResult.getAllErrors()
                .stream()
                .map(error -> {
                    var defaultMessage = error.getDefaultMessage();
                    if (error instanceof FieldError) {
                        var fieldError = (FieldError) error;
                        //return String.format("%s: %s", fieldError.getField(), defaultMessage);
                        return String.format("%s\n", defaultMessage);
                    } else {
                        return defaultMessage;
                    }
                })
                .collect(Collectors.toList()).toString();
    }
}
