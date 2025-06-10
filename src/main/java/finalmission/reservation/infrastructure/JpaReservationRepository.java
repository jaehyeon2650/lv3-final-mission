package finalmission.reservation.infrastructure;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.domain.ReservationTime;
import java.time.LocalDate;
import org.springframework.data.repository.CrudRepository;

public interface JpaReservationRepository extends CrudRepository<Reservation,Long>, ReservationRepository {

    boolean existsByReservationDateAndReservationTime(LocalDate reservationDate, ReservationTime reservationTime);
}
