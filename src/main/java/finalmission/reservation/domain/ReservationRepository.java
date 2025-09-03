package finalmission.reservation.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    boolean existsByReservationDateAndReservationTime(LocalDate reservationDate, ReservationTime reservationTime);

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    Optional<Reservation> findById(Long id);

    void deleteById(Long id);
}
