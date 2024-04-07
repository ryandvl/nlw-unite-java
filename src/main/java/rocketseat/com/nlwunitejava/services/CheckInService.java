package rocketseat.com.nlwunitejava.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rocketseat.com.nlwunitejava.domain.attendee.Attendee;
import rocketseat.com.nlwunitejava.domain.checkIn.CheckIn;
import rocketseat.com.nlwunitejava.domain.checkIn.exceptions.CheckInAlreadyExistsException;
import rocketseat.com.nlwunitejava.repositories.CheckInRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
        this.veriftCheckInExists(attendee.getId());

        CheckIn newCheckIn = new CheckIn();

        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());

        this.checkInRepository.save(newCheckIn);
    }

    private void veriftCheckInExists(String attendeeId) {
        Optional<CheckIn> isCheckedIn = getCheckIn(attendeeId);

        if(isCheckedIn.isPresent()) {
            throw new CheckInAlreadyExistsException("Attendee already checked in");
        }
    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }

}
