package rocketseat.com.nlwunitejava.domain.checkIn.exceptions;

public class CheckInAlreadyExistsException extends RuntimeException {

    public CheckInAlreadyExistsException(String message) {
        super(message);
    }

}
