package rocketseat.com.nlwunitejava.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import rocketseat.com.nlwunitejava.domain.attendee.Attendee;
import rocketseat.com.nlwunitejava.domain.attendee.exceptions.AttendeeAlreadyExistException;
import rocketseat.com.nlwunitejava.domain.attendee.exceptions.AttendeeNotFoundException;
import rocketseat.com.nlwunitejava.domain.checkIn.CheckIn;
import rocketseat.com.nlwunitejava.dto.attendee.AttendeeBadgeResponseDTO;
import rocketseat.com.nlwunitejava.dto.attendee.AttendeeDetails;
import rocketseat.com.nlwunitejava.dto.attendee.AttendeesListResponseDTO;
import rocketseat.com.nlwunitejava.dto.attendee.AttendeeBadgeDTO;
import rocketseat.com.nlwunitejava.repositories.AttendeeRepository;
import rocketseat.com.nlwunitejava.repositories.CheckInRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId) {
        var attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList
                .stream()
                .map(attendee -> {
                    Optional<CheckIn> checkIn = this.checkInService
                            .getCheckIn(attendee.getId());

                    LocalDateTime checkedInAt = checkIn.map(
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

    public void verifyAttendeeSubscription(String email, String eventId) {
        var isAttendeeRegistered = this.attendeeRepository
                .findByEventIdAndEmail(eventId, email);

        if(isAttendeeRegistered.isPresent()) {
            throw new AttendeeAlreadyExistException(
                    "Attendee is already registered"
            );
        }
    }

    public Attendee registerAttendee(Attendee newAttendee) {
        this.attendeeRepository.save(newAttendee);

        return newAttendee;
    }

    public void checkInAttendee(String attendeeId) {
        var attendee = getAttendee(attendeeId);

        this.checkInService.registerCheckIn(attendee);
    }

    private Attendee getAttendee(String attendeeId) {
        return this.attendeeRepository
                .findById(attendeeId)
                .orElseThrow(() -> new AttendeeNotFoundException(
                        "Attendee not found with ID: " + attendeeId
                ));
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(
            String attendeeId,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Attendee attendee = this.getAttendee(attendeeId);

        var uri = uriComponentsBuilder
                .path("/attendees/{attendeeId}/check-in")
                .buildAndExpand(attendeeId)
                .toUri()
                .toString();

        return new AttendeeBadgeResponseDTO(
                new AttendeeBadgeDTO(
                    attendee.getName(),
                    attendee.getEmail(),
                    uri,
                    attendee.getEvent().getId()
                )
        );
    }

}
