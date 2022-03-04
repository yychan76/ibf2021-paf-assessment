package ibf.paf.portfolio.services;

import static ibf.paf.portfolio.Constants.API_URL_FLASK;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ibf.paf.portfolio.models.Price;
import reactor.core.publisher.Mono;


@Service
public class StockDataService {
    private static final String LOG_PREFIX_FLASK_SEARCH = "Searching flask API: ";
    private static final Logger LOG = Logger.getLogger(StockDataService.class.getName());

    private WebClient webClient = WebClient.builder()
            .baseUrl(API_URL_FLASK)
            .build();

    public Mono<String> passThroughStockInfo(String stockName) {
        return this.webClient.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder
                            .path("/info/{stock}")
                            .build(stockName);
                    LOG.info(() -> LOG_PREFIX_FLASK_SEARCH + uri.toString());
                    return uri;
                })
                .retrieve()
                .bodyToMono(String.class);
    }
}
