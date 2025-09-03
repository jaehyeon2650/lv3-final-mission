package finalmission.reservation.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.domain.ReservationTime;
import finalmission.reservation.domain.ReservationTimeRepository;
import finalmission.reservation.dto.request.CreateReservationRequest;
import finalmission.reservation.dto.response.CreateReservationResponse;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.infrastructure.email.EmailSender;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReservationService {

    private static final String RESERVATION_SUBJECT = "회의실 예약이 완료되었습니다!";
    private static final String RESERVATION_VALUE = "회의실 예약이 완료되었습니다. 감사합니다.";

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository, MemberRepository memberRepository,
                              EmailSender emailSender) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.memberRepository = memberRepository;
        this.emailSender = emailSender;
    }

    public CreateReservationResponse createReservation(CreateReservationRequest request, Long memberId) {
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 시간 아이디입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재히지 않은 회원 아이디입니다."));
        if (reservationRepository.existsByReservationDateAndReservationTime(request.date(), reservationTime)) {
            log.info("예약 생성 실패 memberId = {}, date = {}, time = {}, reason = {}", memberId, request.date(),
                    request.timeId(), "이미 예약 존재");
            throw new IllegalArgumentException("해당 시간에 예약이 존재합니다.");
        }

        Reservation reservation = reservationRepository.save(
                Reservation.createReservationWithoutId(LocalDateTime.now(), request.date(),
                        member, reservationTime));

        emailSender.sendSuccessEmail(member.getEmail(), RESERVATION_SUBJECT, RESERVATION_VALUE);
        return CreateReservationResponse.of(reservation);
    }

    public List<ReservationResponse> findAllReservation() {
        return reservationRepository.findAll()
                .stream().map(ReservationResponse::of)
                .toList();
    }

    public ReservationResponse findMyReservation(Long reservationId, Long loginId) {
        Reservation reservation = getMyReservation(reservationId, loginId);
        return ReservationResponse.of(reservation);
    }

    private Reservation getMyReservation(Long reservationId, Long loginId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 예약 번호입니다."));
        if (!reservation.isOwner(loginId)) {
            throw new IllegalArgumentException("본인 예약이 아닙니다.");
        }
        return reservation;
    }

    public void deleteReservation(Long reservationId, Long loginId) {
        Reservation myReservation = getMyReservation(reservationId, loginId);
        reservationRepository.deleteById(myReservation.getId());
    }
}
