package finalmission.member.infrastructure;

import finalmission.member.domain.Email;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface JpaMemberRepository extends CrudRepository<Member, Long>, MemberRepository {

    Optional<Member> findByEmail(Email email);
}
