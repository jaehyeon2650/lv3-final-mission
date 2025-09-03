package finalmission.reservation.domain;

import java.util.Optional;

public interface ReservationTimeRepository {

    Optional<ReservationTime> findById(Long id);
}
