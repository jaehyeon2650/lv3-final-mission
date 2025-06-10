package finalmission.member.dto.request;

import java.time.LocalDate;

public record JoinRequest(LocalDate birth, String email, String password) {
    public JoinRequest{
        if(birth==null){
            throw new IllegalArgumentException("생일을 입력해주세요");
        }
        if (email == null){
            throw new IllegalArgumentException("이메일을 입력해주세요");
        }
        if (password==null){
            throw new IllegalArgumentException("패스워드를 입력해주새요");
        }
    }
}
