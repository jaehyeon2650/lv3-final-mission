package finalmission.login.util;

import finalmission.login.exception.LoginException;
import finalmission.member.domain.Member;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private static final int TOKEN_EXPIRATION_MINUTES = 30;

    private final SecretKey secretKey;

    public JwtProvider(@Value("${jwt.key}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String createAccessToken(Member member) {
        return Jwts.builder()
                .subject(member.getId().toString())
                .expiration(createExpirationDate(LocalDateTime.now()))
                .signWith(secretKey)
                .compact();
    }

    public Long extractIdFromAccessToken(String token) {
        try {
            String id = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return Long.parseLong(id);
        } catch (ExpiredJwtException e) {
            throw new LoginException("만료된 토큰입니다.");
        } catch (JwtException e) {
            throw new LoginException("유효하지 않은 토큰입니다.");
        }
    }

    private Date createExpirationDate(LocalDateTime now) {
        LocalDateTime expirationTime = now.plusMinutes(TOKEN_EXPIRATION_MINUTES);
        return Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
