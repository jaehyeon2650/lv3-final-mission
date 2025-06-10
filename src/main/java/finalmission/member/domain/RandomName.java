package finalmission.member.domain;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomName {

    public String createRandomUsername() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            sb.append((char) (random.nextInt(26) + 65));
        }

        return sb.toString();
    }
}
