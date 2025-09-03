package finalmission.member.service;

import finalmission.member.domain.Email;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.member.dto.request.JoinRequest;
import finalmission.member.dto.response.JoinResponse;
import finalmission.member.infrastructure.RandomUsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final RandomUsernameGenerator usernameGenerator;

    public MemberService(MemberRepository memberRepository, RandomUsernameGenerator usernameGenerator) {
        this.memberRepository = memberRepository;
        this.usernameGenerator = usernameGenerator;
    }

    public JoinResponse createMember(JoinRequest request) {
        String username = usernameGenerator.getRandomUsername();
        Member member = Member.createMemberWithoutId(username, request.birth(), request.email(), request.password());
        if (memberRepository.existsByEmail(new Email(request.email()))) {
            log.info("회원 가입 실패 email = {}, reason = {}", request.email(), "이미 가입된 이메일");
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        Member newMember = memberRepository.save(member);
        return JoinResponse.of(newMember);
    }
}
