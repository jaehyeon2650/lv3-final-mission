package finalmission.login.presentation;

import finalmission.login.dto.request.LoginRequest;
import finalmission.login.service.LoginService;
import finalmission.login.util.CookieManager;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        String accessToken = loginService.loginAndReturnAccessToken(loginRequest);
        cookieManager.addAccessToken(accessToken,response);
        return ResponseEntity.ok().build();
    }
}
