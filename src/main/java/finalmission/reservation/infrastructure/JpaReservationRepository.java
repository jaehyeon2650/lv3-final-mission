package finalmission.reservation.infrastructure;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.domain.ReservationTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface JpaReservationRepository extends CrudRepository<Reservation, Long>, ReservationRepository {

    boolean existsByReservationDateAndReservationTime(LocalDate reservationDate, ReservationTime reservationTime);

    @EntityGraph(attributePaths = {"member", "reservationTime"})
    List<Reservation> findAll();

    @EntityGraph(attributePaths = {"member", "reservationTime"})
    Optional<Reservation> findById(Long id);

    void deleteById(Long id);
}
