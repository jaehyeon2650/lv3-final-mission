package finalmission.reservation.domain;

import java.time.LocalDate;

public interface ReservationRepository {

    boolean existsByReservationDateAndReservationTime(LocalDate reservationDate, ReservationTime reservationTime);

    Reservation save(Reservation reservation);
}
