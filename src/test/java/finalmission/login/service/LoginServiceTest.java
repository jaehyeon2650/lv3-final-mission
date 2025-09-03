package finalmission.login.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import finalmission.login.dto.request.LoginRequest;
import finalmission.login.exception.LoginException;
import finalmission.login.util.JwtProvider;
import finalmission.member.domain.Member;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Import(LoginService.class)
@DataJpaTest
class LoginServiceTest {

    @MockitoBean
    private JwtProvider jwtProvider;

    @Autowired
    private LoginService loginService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("가입된 이메일이 아닌 경우 예외가 발생한다.")
    void loginAndReturnAccessToken_exception() {
        // given
        LoginRequest loginRequest = new LoginRequest("a@naver.com", "1234");
        // when & then
        assertThatThrownBy(() -> loginService.loginAndReturnAccessToken(loginRequest))
                .isInstanceOf(LoginException.class)
                .hasMessage("가입된 이메일이 아닙니다.");
    }

    @Test
    @DisplayName("비밀번호가 잘못된 경우 예외가 발생한다.")
    void loginAndReturnAccessToken_exception_whenNotMatchPassword() {
        // given
        when(jwtProvider.createAccessToken(any()))
                .thenReturn("accessToken");
        em.persist(Member.createMemberWithoutId("a", LocalDate.of(2000, 11, 2), "a@naver.com", "1234"));
        em.flush();
        em.clear();
        LoginRequest loginRequest = new LoginRequest("a@naver.com", "1235");
        // when & then
        assertThatThrownBy(() -> loginService.loginAndReturnAccessToken(loginRequest))
                .isInstanceOf(LoginException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("로그인 성공시 jwt 토큰을 발행한다.")
    void loginAndReturnAccessToken_success() {
        // given
        when(jwtProvider.createAccessToken(any()))
                .thenReturn("accessToken");
        em.persist(Member.createMemberWithoutId("a", LocalDate.of(2000, 11, 2), "a@naver.com", "1234"));
        em.flush();
        em.clear();
        LoginRequest loginRequest = new LoginRequest("a@naver.com", "1234");
        // when
        String token = loginService.loginAndReturnAccessToken(loginRequest);
        // then
        assertThat(token).isEqualTo("accessToken");
    }
}