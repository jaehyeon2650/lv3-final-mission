package finalmission.reservation.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.domain.ReservationTime;
import finalmission.reservation.domain.ReservationTimeRepository;
import finalmission.reservation.dto.request.CreateReservationRequest;
import finalmission.reservation.dto.response.CreateReservationResponse;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final MemberRepository memberRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository, MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.memberRepository = memberRepository;
    }

    public CreateReservationResponse createReservation(CreateReservationRequest request, Long memberId) {
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 시간 아이디입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재히지 않은 회원 아이디입니다."));
        if (reservationRepository.existsByReservationDateAndReservationTime(request.date(), reservationTime)) {
            throw new IllegalArgumentException("해당 시간에 예약이 존재합니다.");
        }

        Reservation reservation = reservationRepository.save(
                Reservation.createReservationWithoutId(LocalDateTime.now(), request.date(),
                        member, reservationTime));

        return CreateReservationResponse.of(reservation);
    }
}
