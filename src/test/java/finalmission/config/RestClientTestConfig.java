package finalmission.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@TestConfiguration
public class RestClientTestConfig {

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
}
