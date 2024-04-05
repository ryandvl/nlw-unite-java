package rocketseat.com.nlwunitejava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rocketseat.com.nlwunitejava.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, String> {



}
