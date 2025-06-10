package finalmission.member.domain;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BirthTest {

    @ParameterizedTest
    @CsvSource({"2024-06-10", "2006-06-11", "2007-07-10"})
    @DisplayName("성인이 아니면 예외가 발생햐아한다.")
    void adultExceptionTest(LocalDate birth) {
        LocalDate now = LocalDate.of(2025, 6, 10);
        Assertions.assertThatThrownBy(() -> Birth.createAdultBirth(birth, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("19세 이상 성인만 이용가능합니다.");
    }
}