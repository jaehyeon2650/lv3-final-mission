package finalmission.reservation.infrastructure.email.dto.response;

import java.util.List;

public record ErrorResponse(
        List<Error> errors
) {

    public String getFistErrorMessage() {
        return errors.get(0).message();
    }
}
