package finalmission.login.service;

import finalmission.login.dto.request.LoginRequest;
import finalmission.login.exception.LoginException;
import finalmission.login.util.JwtProvider;
import finalmission.member.domain.Email;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public LoginService(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    public String loginAndReturnAccessToken(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(new Email(loginRequest.email()))
                .orElseThrow(() -> new LoginException("가입된 이메일이 아닙니다."));
        if (member.isSamePassword(loginRequest.password())) {
            return jwtProvider.createAccessToken(member);
        }
        throw new LoginException("비밀번호가 일치하지 않습니다.");
    }
}
