package rocketseat.com.nlwunitejava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rocketseat.com.nlwunitejava.domain.attendee.Attendee;

import java.util.List;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {

    List<Attendee> findByEventId(String eventId);

}
