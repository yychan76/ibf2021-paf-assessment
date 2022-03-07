package ibf.paf.portfolio;

import static ibf.paf.portfolio.Constants.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

@Configuration
public class AppConfig {

    @Bean
    public ExchangeStrategies webClientStrategies() {
        final int size = WEB_CLIENT_MAX_BUFFER_MB;
        return ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();
    }

}
