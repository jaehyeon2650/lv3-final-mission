package finalmission.reservation.infrastructure.email.dto.request;

import java.util.List;

public record Personalization(
        List<Email> to,
        String subject
) {
}
