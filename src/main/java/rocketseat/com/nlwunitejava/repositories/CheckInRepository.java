package rocketseat.com.nlwunitejava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rocketseat.com.nlwunitejava.domain.checkIn.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
}
