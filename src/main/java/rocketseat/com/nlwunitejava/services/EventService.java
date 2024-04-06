package rocketseat.com.nlwunitejava.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rocketseat.com.nlwunitejava.domain.attendee.Attendee;
import rocketseat.com.nlwunitejava.domain.event.Event;
import rocketseat.com.nlwunitejava.domain.event.exceptions.EventNotFoundException;
import rocketseat.com.nlwunitejava.dto.event.EventIdDTO;
import rocketseat.com.nlwunitejava.dto.event.EventRequestDTO;
import rocketseat.com.nlwunitejava.dto.event.EventResponseDTO;
import rocketseat.com.nlwunitejava.repositories.AttendeeRepository;
import rocketseat.com.nlwunitejava.repositories.EventRepository;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId) {
        var event = this.eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(
                        "Event not found with ID: " + eventId
                ));

        List<Attendee> attendeeList = this.attendeeService
                .getAllAttendeesFromEvent(eventId);

        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventRequestDTO) {
        var newEvent = new Event();

        newEvent.setTitle(eventRequestDTO.title());
        newEvent.setDetails(eventRequestDTO.details());
        newEvent.setMaximumAttendees(eventRequestDTO.maximumAttendees());

        newEvent.setSlug(createSlug(eventRequestDTO.title()));

        this.eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());
    }

    private String createSlug(String text) {
        String normalize = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalize.replaceAll(
                "[\\p{InCOMBINING_DIACRITICAL_MARKS}]",
                ""
        ).replaceAll(
                "[^\\w\\s]",
                ""
        ).replaceAll(
                "\\s+",
                "-"
        ).toLowerCase();
    }

}