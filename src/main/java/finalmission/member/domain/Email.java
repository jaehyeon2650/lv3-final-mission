package finalmission.member.domain;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Email {
    private static final String EMAIL_ANNOTATION = "@";
    private String email;

    public Email(String email) {
        validateEmailFormat(email);
        this.email = email;
    }

    protected Email() {

    }

    private void validateEmailFormat(String email) {
        if (!email.contains(EMAIL_ANNOTATION)) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다(@ 미포함).");
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email email1)) {
            return false;
        }
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
