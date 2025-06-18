package finalmission.reservation.infrastructure.email;

import finalmission.reservation.infrastructure.email.dto.request.SendEmailRequest;
import finalmission.reservation.infrastructure.email.dto.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class EmailSender {

    private static final String AUTHORIZATION_HEADER = "Bearer ";
    private static final String SENDER_EMAIL = "ind07152@gmail.com";
    private static final String EMAIL_TYPE = "text/plain";

    private final String apiKey;
    private final RestClient restClient;

    public EmailSender(@Value("${email.api.key}") String apiKey, @Qualifier("restClient") RestClient restClient) {
        this.apiKey = apiKey;
        this.restClient = restClient;
    }

    public void sendSuccessEmail(String toEmail, String subject, String content) {
        try {
            restClient.post()
                    .uri("https://api.sendgrid.com/v3/mail/send")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER + apiKey)
                    .body(SendEmailRequest.ofSingleRecipient(SENDER_EMAIL, toEmail, subject, EMAIL_TYPE, content))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            System.out.println(errorResponse.getFistErrorMessage());
        }
    }
}
