package finalmission.reservation.dto.request;

import java.time.LocalDate;

public record CreateReservationRequest(LocalDate date, Long timeId) {
    public CreateReservationRequest {
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜는 필수입니다.");
        }
        if (timeId == null) {
            throw new IllegalArgumentException("예약 시간 아이디는 필수입니다.");
        }
    }
}
