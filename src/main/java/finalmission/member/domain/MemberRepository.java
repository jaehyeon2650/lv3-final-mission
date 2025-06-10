package finalmission.member.domain;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findById(Long id);

    Member save(Member member);

    Optional<Member> findByEmail(Email email);
}
