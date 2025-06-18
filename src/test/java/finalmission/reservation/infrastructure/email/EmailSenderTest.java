package finalmission.reservation.infrastructure.email;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import finalmission.config.RestClientTestConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;

@Import({EmailSender.class, RestClientTestConfig.class})
@RestClientTest
class EmailSenderTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private EmailSender emailSender;

    @Test
    @DisplayName("성공적으로 email api를 호출한다.")
    void emailSender_test() {
        // given
        server.expect(requestTo("https://api.sendgrid.com/v3/mail/send"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(HttpHeaders.AUTHORIZATION, Matchers.startsWith("Bearer ")))
                .andRespond(withStatus(HttpStatus.ACCEPTED));
        // when
        emailSender.sendSuccessEmail("ind07152@gmail.com", "a", "a");
        // then
        server.verify();
    }
}