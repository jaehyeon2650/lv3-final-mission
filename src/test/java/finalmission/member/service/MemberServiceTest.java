package finalmission.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import finalmission.member.domain.Member;
import finalmission.member.dto.request.JoinRequest;
import finalmission.member.dto.response.JoinResponse;
import finalmission.member.infrastructure.RandomUsernameGenerator;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Import(MemberService.class)
@DataJpaTest
class MemberServiceTest {

    @MockitoBean
    private RandomUsernameGenerator randomUsernameGenerator;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("이미 가입된 이메일에 대해서는 가입이 불가능하다.")
    void createMember_exception() {
        // given
        when(randomUsernameGenerator.getRandomUsername())
                .thenReturn("asd");
        String email = "ind07152@naver.com";
        em.persist(Member.createMemberWithoutId("a", LocalDate.of(2000, 11, 2), email, "1234"));
        em.flush();
        em.clear();
        JoinRequest joinRequest = new JoinRequest(LocalDate.of(2000, 11, 2), email, "1234");
        // when & then
        assertThatThrownBy(() -> memberService.createMember(joinRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 가입된 이메일입니다.");
    }

    @Test
    @DisplayName("정상적으로 생성하면 결과를 반환한다.")
    void createMember_success() {
        // given
        when(randomUsernameGenerator.getRandomUsername())
                .thenReturn("asd");
        JoinRequest joinRequest = new JoinRequest(LocalDate.of(2000, 11, 2), "ind07152@naver.com", "1234");
        // when
        JoinResponse response = memberService.createMember(joinRequest);
        // then
        assertThat(response.id()).isNotNull();
        assertThat(response.username()).isEqualTo("asd");
    }
}