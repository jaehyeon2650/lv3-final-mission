package finalmission.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import finalmission.reservation.dto.request.CreateReservationRequest;
import finalmission.reservation.dto.response.CreateReservationResponse;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.infrastructure.email.EmailSender;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

@Import(ReservationService.class)
@DataJpaTest
@Sql(scripts = "/data/reservationTest.sql")
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @MockitoBean
    private EmailSender emailSender;

    @Test
    @DisplayName("예약 생성시 존재하지 않은 맴버 아이디가 들어오면 예외가 발생한다.")
    void createReservation_memberException() {
        // given
        CreateReservationRequest request = new CreateReservationRequest(LocalDate.now().plusDays(1), 1L);
        Long loginId = 3L;
        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(request, loginId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재히지 않은 회원 아이디입니다.");
    }

    @Test
    @DisplayName("예약 생성시 존재하지 않은 시간 아이디가 들어오면 예외가 발생한다.")
    void createReservation_timeException() {
        // given
        CreateReservationRequest request = new CreateReservationRequest(LocalDate.now().plusDays(1), 100L);
        Long loginId = 1L;
        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(request, loginId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않은 시간 아이디입니다.");
    }

    @Test
    @DisplayName("예약 생성시 이미 예약이 존재하는 경우 예외가 발생한다.")
    void createReservation_duplicationException() {
        // given
        CreateReservationRequest request = new CreateReservationRequest(LocalDate.of(2025, 6, 11), 3L);
        Long loginId = 1L;
        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(request, loginId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 시간에 예약이 존재합니다.");
    }

    @Test
    @DisplayName("성공적으로 예약을 생성한다.")
    void creatReservation_success() {
        // given
        LocalDate reservationDate = LocalDate.now().plusDays(1);
        CreateReservationRequest request = new CreateReservationRequest(reservationDate, 1L);
        Long loginId = 1L;
        // when
        CreateReservationResponse response = reservationService.createReservation(request, loginId);
        // then
        verify(emailSender, times(1)).sendSuccessEmail(any(), any(), any());
        assertThat(response.username()).isEqualTo("cogi");
        assertThat(response.date()).isEqualTo(reservationDate);
        assertThat(response.time()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void findAllReservation(){
        // when
        List<ReservationResponse> reservations = reservationService.findAllReservation();
        // then
        assertThat(reservations).hasSize(4);
    }

    @Test
    @DisplayName("다른 사람의 예약을 조회하려고 하면 예외가 발생한다.")
    void getMyReservation_exception_notMine(){
        assertThatThrownBy(()->reservationService.findMyReservation(1L,2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("본인 예약이 아닙니다.");
    }

    @Test
    @DisplayName("없는 예약을 조회하려고하면 예외가 발생한다.")
    void getMyReservation_exception_noReservation(){
        assertThatThrownBy(()->reservationService.findMyReservation(100L,2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않은 예약 번호입니다.");
    }

    @Test
    @DisplayName("본인의 예약을 조회한다.")
    void getMyReservation(){
        // given
        ReservationResponse expected = new ReservationResponse(1L,"cogi",LocalDate.of(2025,6,11),LocalTime.of(10,0));
        // when
        ReservationResponse myReservation = reservationService.findMyReservation(1L, 1L);
        // then
        assertThat(myReservation).isEqualTo(expected);
    }
}