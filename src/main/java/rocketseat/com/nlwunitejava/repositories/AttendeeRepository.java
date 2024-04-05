package rocketseat.com.nlwunitejava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rocketseat.com.nlwunitejava.domain.attendee.Attendee;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {

}
