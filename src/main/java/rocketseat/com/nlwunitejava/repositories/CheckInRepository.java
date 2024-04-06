package rocketseat.com.nlwunitejava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rocketseat.com.nlwunitejava.domain.checkIn.CheckIn;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {

    Optional<CheckIn> findByAttendeeId(String attendeeId);

}
