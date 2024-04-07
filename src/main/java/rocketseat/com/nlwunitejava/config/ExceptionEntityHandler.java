package rocketseat.com.nlwunitejava.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rocketseat.com.nlwunitejava.domain.attendee.exceptions.AttendeeAlreadyExistException;
import rocketseat.com.nlwunitejava.domain.attendee.exceptions.AttendeeNotFoundException;
import rocketseat.com.nlwunitejava.domain.checkIn.exceptions.CheckInAlreadyExistsException;
import rocketseat.com.nlwunitejava.domain.event.exceptions.EventFullException;
import rocketseat.com.nlwunitejava.domain.event.exceptions.EventNotFoundException;
import rocketseat.com.nlwunitejava.dto.general.ErrorResponseDTO;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<?> handleEventNotFound(
            EventNotFoundException exception
    ) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFull(
            EventFullException exception
    ) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponseDTO(
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity<?> handleAttendeeNotFound(
            AttendeeNotFoundException exception
    ) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity<?> handleAttendeeAlreadyExists(
            AttendeeAlreadyExistException exception
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity<?> handleCheckInAlreadyExists(
            CheckInAlreadyExistsException exception
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
