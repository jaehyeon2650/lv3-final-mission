package finalmission.common.exception.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String uri,
        String errorMessage
) {
}
