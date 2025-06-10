package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reservationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id")
    private ReservationTime reservationTime;

    public Reservation(Long id, LocalDate reservationDate, Member member, ReservationTime reservationTime) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.member = member;
        this.reservationTime = reservationTime;
    }

    protected Reservation() {

    }

    public static Reservation createReservationWithoutId(LocalDateTime now, LocalDate reservationDate, Member member, ReservationTime reservationTime){
        validateDateAndTime(now,reservationDate,reservationTime.getTime());
        return new Reservation(null,reservationDate,member,reservationTime);
    }

    private static void validateDateAndTime(LocalDateTime now, LocalDate reservationDate, LocalTime reservationTime){
        LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate,reservationTime);
        if(reservationDateTime.isBefore(now)){
            throw new IllegalArgumentException("지난 시간에 대해서는 예약이 불가능합니다.");
        }
    }

    public boolean isOwner(Long memberId){
        return this.member.getId().equals(memberId);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public String getReservationName(){
        return member.getUsername();
    }

    public LocalTime getReservationTIme(){
        return reservationTime.getTime();
    }

    public Member getMember() {
        return member;
    }
}
