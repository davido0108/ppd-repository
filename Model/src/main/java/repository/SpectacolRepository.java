package repository;

import domain.Spectacol;
import domain.Vanzare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpectacolRepository extends JpaRepository<Spectacol, Long> {
}
