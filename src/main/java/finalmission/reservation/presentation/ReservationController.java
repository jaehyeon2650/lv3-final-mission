package finalmission.reservation.presentation;

import finalmission.login.resolver.LoginMember;
import finalmission.reservation.dto.request.CreateReservationRequest;
import finalmission.reservation.dto.response.CreateReservationResponse;
import finalmission.reservation.service.ReservationService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<CreateReservationResponse> createReservation(@LoginMember Long loginId, @RequestBody
                                                                       CreateReservationRequest request){
        CreateReservationResponse response = reservationService.createReservation(request, loginId);
        return ResponseEntity.created(URI.create("/reservations/"+response.id())).body(response);
    }
}
