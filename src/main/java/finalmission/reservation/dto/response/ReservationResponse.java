package finalmission.reservation.dto.response;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(Long id, String username, LocalDate date, LocalTime time) {

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getReservationName(),
                reservation.getReservationDate(), reservation.getReservationTIme());
    }
}
