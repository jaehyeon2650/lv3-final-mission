package finalmission.member.infrastructure;

import finalmission.member.domain.RandomName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class RandomUsernameGenerator {
    private static final String BASE_API_URL = "https://randommer.io/api";
    private static final String KEY_HEADER = "X-Api-Key";

    private final RestClient restClient;
    private final RandomName randomName;
    private final String apiKey;

    public RandomUsernameGenerator(RestClient restClient, RandomName randomName, @Value("${api.key}") String key) {
        this.restClient = restClient;
        this.randomName = randomName;
        this.apiKey = key;
    }

    public String getRandomUsername() {
        try {
            return restClient.get()
                    .uri(BASE_API_URL + "/Name?nameType=firstname&quantity=1")
                    .header(KEY_HEADER, apiKey)
                    .retrieve()
                    .body(String[].class)
                    [0];
        } catch (Exception e) {
            log.error("[ERROR] 랜덤 이름 생성 API 실패");
            return randomName.createRandomUsername();
        }
    }
}
