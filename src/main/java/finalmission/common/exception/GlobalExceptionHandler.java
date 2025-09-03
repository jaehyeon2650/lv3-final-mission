package finalmission.common.exception;

import finalmission.common.exception.dto.ErrorResponse;
import finalmission.login.exception.LoginException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e,
                                                                        HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new ErrorResponse(request.getRequestURI(), e.getMessage()));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(LoginException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(request.getRequestURI(), e.getMessage()));
    }
}
