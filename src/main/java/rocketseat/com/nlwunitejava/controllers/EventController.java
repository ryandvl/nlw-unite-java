package rocketseat.com.nlwunitejava.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import rocketseat.com.nlwunitejava.dto.attendee.AttendeesListResponseDTO;
import rocketseat.com.nlwunitejava.dto.event.EventIdDTO;
import rocketseat.com.nlwunitejava.dto.event.EventRequestDTO;
import rocketseat.com.nlwunitejava.dto.event.EventResponseDTO;
import rocketseat.com.nlwunitejava.services.AttendeeService;
import rocketseat.com.nlwunitejava.services.EventService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(
            @PathVariable String eventId
    ) {
        var eventResponse = this.eventService.getEventDetail(eventId);

        return ResponseEntity.ok(eventResponse);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(
            @RequestBody EventRequestDTO body,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var eventIdDTO = this.eventService.createEvent(body);

        var uri = uriComponentsBuilder
                .path("/events/{id}")
                .buildAndExpand(eventIdDTO.eventId())
                .toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @GetMapping("/attendees/{attendeeId}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(
            @PathVariable String attendeeId
    ) {
        var attendeesListResponse = this.attendeeService.getEventsAttendee(attendeeId);

        return ResponseEntity.ok(attendeesListResponse);
    }
}
