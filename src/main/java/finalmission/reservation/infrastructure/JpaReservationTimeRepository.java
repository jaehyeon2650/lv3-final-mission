package finalmission.reservation.infrastructure;

import finalmission.reservation.domain.ReservationTime;
import finalmission.reservation.domain.ReservationTimeRepository;
import org.springframework.data.repository.CrudRepository;

public interface JpaReservationTimeRepository extends CrudRepository<ReservationTime, Long>, ReservationTimeRepository {
}
