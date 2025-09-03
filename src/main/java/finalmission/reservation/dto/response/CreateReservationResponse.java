package finalmission.reservation.dto.response;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationResponse(Long id, String username, LocalDate date, LocalTime time) {
    public static CreateReservationResponse of(Reservation reservation) {
        return new CreateReservationResponse(reservation.getId(), reservation.getReservationName(),
                reservation.getReservationDate(), reservation.getReservationTIme());
    }
}
