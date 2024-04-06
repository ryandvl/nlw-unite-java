package rocketseat.com.nlwunitejava.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rocketseat.com.nlwunitejava.domain.attendee.Attendee;
import rocketseat.com.nlwunitejava.domain.checkIn.CheckIn;
import rocketseat.com.nlwunitejava.dto.attendee.AttendeeDetails;
import rocketseat.com.nlwunitejava.dto.attendee.AttendeesListResponseDTO;
import rocketseat.com.nlwunitejava.repositories.AttendeeRepository;
import rocketseat.com.nlwunitejava.repositories.CheckInRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInRepository checkInRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId) {
        var attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList
                .stream()
                .map(attendee -> {
                    Optional<CheckIn> checkIn = this.checkInRepository
                            .findByAttendeeId(attendee.getId());

                    LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(
                            CheckIn::getCreatedAt
                    ).orElse(null);

                    return new AttendeeDetails(
                            attendee.getId(),
                            attendee.getName(),
                            attendee.getEmail(),
                            attendee.getCreatedAt(),
                            checkedInAt
                    );
                }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

}
