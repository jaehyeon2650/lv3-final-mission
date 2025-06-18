package finalmission.login.presentation;

import finalmission.login.dto.request.LoginRequest;
import finalmission.login.service.LoginService;
import finalmission.login.util.CookieManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    private final LoginService loginService;
    private final CookieManager cookieManager;

    public LoginController(LoginService loginService, CookieManager cookieManager) {
        this.loginService = loginService;
        this.cookieManager = cookieManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        log.info("로그인 시도 email = {}",loginRequest.email());
        String accessToken = loginService.loginAndReturnAccessToken(loginRequest);
        cookieManager.addAccessToken(accessToken,response);
        log.info("로그인 성공 email = {}",loginRequest.email());
        return ResponseEntity.ok().build();
    }
}
