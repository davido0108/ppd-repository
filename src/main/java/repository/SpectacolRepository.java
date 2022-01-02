package repository;

import domain.Spectacol;
import domain.Vanzare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpectacolRepository extends JpaRepository<Spectacol, Long> {
}
