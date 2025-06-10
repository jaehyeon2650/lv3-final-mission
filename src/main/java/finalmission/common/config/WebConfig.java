package finalmission.common.config;

import finalmission.login.resolver.LoginArgumentResolver;
import finalmission.login.util.CookieManager;
import finalmission.login.util.JwtProvider;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CookieManager cookieManager;
    private final JwtProvider jwtProvider;

    public WebConfig(CookieManager cookieManager, JwtProvider jwtProvider) {
        this.cookieManager = cookieManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver(cookieManager,jwtProvider));
    }
}
