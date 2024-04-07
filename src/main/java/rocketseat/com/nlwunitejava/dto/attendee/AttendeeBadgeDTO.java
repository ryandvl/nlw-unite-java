package rocketseat.com.nlwunitejava.dto.attendee;

public record AttendeeBadgeDTO(
        String name,
        String email,
        String checkInURL,
        String eventId
) { }
