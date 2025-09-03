package finalmission.member.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.member.domain.Email;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class JpaMemberRepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByEmail_isExisted() {
        // given
        String email = "ind7152@naver.com";
        em.persist(Member.createMemberWithoutId("a", LocalDate.of(2000, 11, 2), email, "1234"));
        em.flush();
        em.clear();
        // when
        Optional<Member> findMember = memberRepository.findByEmail(new Email(email));
        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getUsername()).isEqualTo("a");
    }

    @Test
    void findByEmail_isNotExisted() {
        // given
        String email = "ind7152@naver.com";
        // when
        Optional<Member> findMember = memberRepository.findByEmail(new Email(email));
        // then
        assertThat(findMember).isEmpty();
    }

}