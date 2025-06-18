package finalmission.member.infrastructure;

import finalmission.member.domain.RandomName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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
            return randomName.createRandomUsername();
        }
    }
}
