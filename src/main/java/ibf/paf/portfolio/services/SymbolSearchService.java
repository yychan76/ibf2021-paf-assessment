package ibf.paf.portfolio.services;

import static ibf.paf.portfolio.Constants.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ibf.paf.portfolio.models.Symbol;
import reactor.core.publisher.Mono;

@Service
public class SymbolSearchService {
    private static final Logger LOG = Logger.getLogger(SymbolSearchService.class.getName());

    private String apikey = "";

    public SymbolSearchService() {
        Optional<String> opt = Optional.ofNullable(System.getenv(ENV_SEARCH_KEY));
        apikey = opt.filter(v -> !v.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("ENV_SEARCH_KEY not provided"));
    }

    public Mono<List<Symbol>> searchSymbols(String keywords) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-host", HOST_SEARCH);
        headers.add("x-rapidapi-key", apikey);

        WebClient webClient = WebClient.builder()
                .baseUrl(API_URL_SEARCH)
                .defaultHeaders(httpHeaders -> httpHeaders.addAll(headers))
                .build();

        return webClient.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder
                            .path("/query")
                            .queryParam("keywords", keywords)
                            .queryParam("function", "SYMBOL_SEARCH")
                            .queryParam("datatype", "json")
                            .build();
                    LOG.info("Searching external API: %s".formatted(uri.toString()));
                    return uri;
                })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(JsonNode.class)
                .map(json -> json.path("bestMatches"))
                .flatMap(jsonArr -> Mono.just(
                        // iterate through each element of the bestMatches array and convert into stream
                        StreamSupport.stream(jsonArr.spliterator(), false)
                                .map(item -> {
                                    // the array elements have different field names from our Symbol record
                                    LOG.info("item: %s".formatted(item));
                                    String symbolTxt = item.get("1. symbol").asText();
                                    String name = item.get("2. name").asText();
                                    String type = item.get("3. type").asText();
                                    String region = item.get("4. region").asText();
                                    String currency = item.get("8. currency").asText();
                                    return new Symbol(symbolTxt, name, type, region, currency);
                                })
                                .toList()))
                .log();
    }

}
