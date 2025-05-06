package ac.at.tgm.grocerylist_gradl2;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Globale Fehlerbehandlung für REST-Controller.
 * Diese Klasse fängt Exceptions ab, die bei Validierungsfehlern (z.B. @Valid, @NotBlank) auftreten,
 * und gibt eine benutzerfreundliche Fehlermeldung zurück.
 *
 * @author Furkan
 * @version 2025-04-30
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Behandelt Fehler, die durch ungültige Eingaben bei @Valid entstehen.
     * Gibt die Fehlermeldung des ungültigen Felds zurück (z.B. "Name darf nicht leer sein").
     * @param ex Die Exception, die vom Spring Framework bei Validierungsfehlern geworfen wird.
     * @return ResponseEntity mit HTTP 400 (Bad Request) und einer Fehlermeldung im Text.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body("Ungültige Anfrage");
    }

    /**
     * Behandelt ConstraintViolationExceptions, z.B. wenn einzelne Parameter nicht den Anforderungen entsprechen.
     * Wird z,B. bei @RequestParam-Validierungen benötigt.
     *
     * @param ex Die ConstraintViolationException.
     * @return ResponseEntity mit HTTP 400 (Bad Request) und der Fehlermeldung.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
