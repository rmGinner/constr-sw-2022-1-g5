package br.rmginner.interceptors;

import br.rmginner.utils.BusinessException;
import br.rmginner.utils.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResponseErrorInterceptor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity
                .badRequest()
                .body(ResponseError.builder().errors(errors).status(HttpStatus.BAD_REQUEST.value()).build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseError> handleJsonParseException(
            HttpMessageNotReadableException ex) {

        Map<String, String> errors = new HashMap<>();
        errors.put("body", "Invalid request body format.");
        return ResponseEntity
                .badRequest()
                .body(ResponseError.builder().errors(errors).status(HttpStatus.BAD_REQUEST.value()).build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseError> handleBusinessException(BusinessException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("body", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ResponseError.builder().errors(errors).status(HttpStatus.BAD_REQUEST.value()).build());
    }

}
