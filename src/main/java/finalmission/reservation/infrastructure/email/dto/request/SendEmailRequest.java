package finalmission.reservation.infrastructure.email.dto.request;

import java.util.List;

public record SendEmailRequest(
        List<Personalization> personalizations,
        Email from,
        List<Content> content
) {

    public static SendEmailRequest ofSingleRecipient(String fromEmail, String toEmail, String subject,
                                                     String type, String value) {
        return new SendEmailRequest(
                List.of(new Personalization(List.of(new Email(toEmail)), subject)),
                new Email(fromEmail),
                List.of(new Content(type, value))
        );
    }
}
