package finalmission.member.presentation;

import finalmission.member.dto.request.JoinRequest;
import finalmission.member.dto.response.JoinResponse;
import finalmission.member.service.MemberService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest joinRequest) {
        JoinResponse response = memberService.createMember(joinRequest);
        return ResponseEntity.created(URI.create("/members/" + response.id())).body(response);
    }
}
