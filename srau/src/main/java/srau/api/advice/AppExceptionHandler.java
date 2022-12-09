package srau.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import srau.api.exception.BussinesLogicException;
import srau.api.exception.ElementNotFoundException;
import srau.api.exception.ElementTakenException;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<String> handleElementNotFound(ElementNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ElementTakenException.class)
    public ResponseEntity<String> handleElementTaken(ElementTakenException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BussinesLogicException.class)
    public ResponseEntity<String> handleBussinesLogic(BussinesLogicException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
