package rocketseat.com.nlwunitejava.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import rocketseat.com.nlwunitejava.dto.attendee.AttendeeBadgeResponseDTO;
import rocketseat.com.nlwunitejava.services.AttendeeService;

import java.net.URI;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;

    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(
            @PathVariable String attendeeId,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var response = this.attendeeService.getAttendeeBadge(
                attendeeId,
                uriComponentsBuilder
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity<URI> registerCheckIn(
            @PathVariable String attendeeId,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        this.attendeeService.checkInAttendee(attendeeId);

        var uri = uriComponentsBuilder
                .path("attendees/{attendeeId}/badge")
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
