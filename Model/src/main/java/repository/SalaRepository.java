package repository;

import domain.Sala;
import domain.Vanzare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
}
