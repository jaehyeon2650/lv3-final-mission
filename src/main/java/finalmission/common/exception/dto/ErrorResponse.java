package finalmission.common.exception.dto;

public record ErrorResponse(
        String uri,
        String errorMessage
) {
}
