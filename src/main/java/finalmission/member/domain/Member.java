package finalmission.member.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Embedded
    private Birth birth;
    @Embedded
    private Email email;
    private String password;

    public Member(Long id, String username, Birth birth, Email email, String password) {
        this.id = id;
        this.username = username;
        this.birth = birth;
        this.email = email;
        this.password = password;
    }

    public static Member createMemberWithoutId(String username, LocalDate birth, String email, String password) {
        return new Member(null, username, Birth.createAdultBirth(birth, LocalDate.now()), new Email(email), password);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail(){
        return email.getEmail();
    }

    protected Member() {

    }
}
