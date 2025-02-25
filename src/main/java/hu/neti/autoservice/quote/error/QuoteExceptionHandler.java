package hu.neti.autoservice.quote.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class QuoteExceptionHandler {

    @ExceptionHandler(PdfGenerationException.class)
    public ResponseEntity<String> handleValidationException(PdfGenerationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PDF generation exception: " + e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationErrorHandler(ConstraintViolationException ex){
        List<String> errorsList = new ArrayList<>(ex.getConstraintViolations().size());
        ex.getConstraintViolations().forEach(error -> errorsList.add(error.toString()));
        return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
    }
}
