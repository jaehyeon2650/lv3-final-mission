package finalmission.member.domain;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.Period;

@Embeddable
public class Birth {
    private static final Integer ADULT_AGE = 19;

    private LocalDate birth;

    private Birth(LocalDate birth) {
        this.birth = birth;
    }

    protected Birth() {

    }

    public static Birth createAdultBirth(LocalDate birth, LocalDate now) {
        validateBirth(birth, now);
        return new Birth(birth);
    }

    private static void validateBirth(LocalDate birth, LocalDate now) {
        Period period = Period.between(birth, now);
        if (period.getYears() < ADULT_AGE) {
            throw new IllegalArgumentException("19세 이상 성인만 이용가능합니다.");
        }
    }

    public LocalDate getBirth() {
        return birth;
    }
}
