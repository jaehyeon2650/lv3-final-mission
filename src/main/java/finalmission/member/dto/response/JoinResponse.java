package finalmission.member.dto.response;

import finalmission.member.domain.Member;

public record JoinResponse(Long id, String username) {

    public static JoinResponse of(Member member) {
        return new JoinResponse(member.getId(), member.getUsername());
    }
}
