package finalmission.member.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import finalmission.config.RestClientTestConfig;
import finalmission.member.domain.RandomName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.MockRestServiceServer;

@Import({RestClientTestConfig.class, RandomUsernameGenerator.class})
@RestClientTest
class RandomUsernameGeneratorTest {

    @Autowired
    private MockRestServiceServer server;

    @MockitoBean
    private RandomName randomName;

    @Autowired
    private RandomUsernameGenerator randomUsernameGenerator;

    @Test
    @DisplayName("외부 API로 랜덤 유저 이름 생성 테스트")
    void getRandomUsername_success() {
        // given
        String expectedName = "asd";
        server.expect(requestTo("https://randommer.io/api/Name?nameType=firstname&quantity=1"))
                .andRespond(withSuccess()
                        .body(expectedName));
        // when
        assertThat(randomUsernameGenerator.getRandomUsername())
                .isEqualTo(expectedName);
    }

    @Test
    @DisplayName("외부 API로 실패 테스트")
    void getRandomUsername_fail() {
        // given
        String expectedName = "aa";
        server.expect(requestTo("https://randommer.io/api/Name?nameType=firstname&quantity=1"))
                .andRespond(withBadRequest());
        when(randomName.createRandomUsername())
                .thenReturn(expectedName);
        // when
        assertThat(randomUsernameGenerator.getRandomUsername())
                .isEqualTo(expectedName);
    }
}