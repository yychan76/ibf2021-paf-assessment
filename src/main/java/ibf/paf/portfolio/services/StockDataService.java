package ibf.paf.portfolio.services;

import static ibf.paf.portfolio.Constants.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import ibf.paf.portfolio.models.Dividend;
import ibf.paf.portfolio.models.Price;
import ibf.paf.portfolio.models.Split;
import reactor.core.publisher.Mono;

@Service
public class StockDataService {
    private static final String LOG_PREFIX_FLASK_SEARCH = "Searching flask API: ";
    private static final Logger LOG = Logger.getLogger(StockDataService.class.getName());

    private WebClient webClient = WebClient.builder()
            .baseUrl(API_URL_FLASK)
            .exchangeStrategies(
                    ExchangeStrategies.builder()
                            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(WEB_CLIENT_MAX_BUFFER_MB)).build())
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

    public Mono<List<Price>> getPrices(String stockName) {
        return this.getPrices(stockName, "max", "1d");
    }

    public Mono<List<Price>> getPrices(String stockName, String period) {
        return this.getPrices(stockName, period, "1d");
    }

    public Mono<List<Price>> getPrices(String stockName, String period, String interval) {
        return this.webClient.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder
                            .path("/history/{stock}")
                            .queryParam("period", period)
                            .queryParam("interval", interval)
                            .build(stockName);
                    LOG.info(() -> LOG_PREFIX_FLASK_SEARCH + uri.toString());
                    return uri;
                })
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.path("prices"))
                .flatMap(jsonArr -> Mono.just(
                        // iterate through each element of the prices array and convert into stream
                        StreamSupport.stream(jsonArr.spliterator(), false)
                                .map(item -> {
                                    // custom mapping to Price field to convert data type
                                    // LOG.info(() -> "item: " + item);
                                    float open = item.get("open").floatValue();
                                    float close = item.get("close").floatValue();
                                    float low = item.get("low").floatValue();
                                    float high = item.get("high").floatValue();
                                    int volume = item.get("volume").asInt();
                                    BigDecimal timestamp = BigDecimal.valueOf(item.get("timestamp").doubleValue());
                                    ZonedDateTime datetime;

                                    try {
                                        datetime = ZonedDateTime.parse(item.get("date").asText(),
                                                DateTimeFormatter.ISO_DATE_TIME);
                                    } catch (DateTimeParseException e) {
                                        // couldn't parse to a ZoneDateTime, try LocalDateTime
                                        LocalDateTime dt = LocalDateTime.parse(item.get("date").asText(),
                                                DateTimeFormatter.ISO_DATE_TIME);
                                        // convert to a timezone
                                        datetime = dt.atZone(ZoneId.of("UTC"));
                                    }

                                    return new Price(open, close, low, high, volume, timestamp, datetime);
                                })
                                .toList()))
                .log();

    }

    public Mono<List<Price>> getPrices(String stockName, LocalDate start, String interval) {
        return this.getPrices(stockName, start, LocalDate.now(), interval);
    }

    public Mono<List<Price>> getPrices(String stockName, LocalDate start, LocalDate end) {
        return this.getPrices(stockName, start, end, "1d");
    }

    public Mono<List<Price>> getPrices(String stockName, LocalDate start) {
        return this.getPrices(stockName, start, LocalDate.now(), "1d");
    }

    public Mono<List<Price>> getPrices(String stockName, LocalDate start, LocalDate end, String interval) {
        return this.webClient.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder
                            .path("/history/{stock}")
                            .queryParam("interval", interval)
                            .queryParam("start", start.toString())
                            .queryParam("end", end.toString())
                            .build(stockName);
                    LOG.info(() -> LOG_PREFIX_FLASK_SEARCH + uri.toString());
                    return uri;
                })
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.path("prices"))
                .flatMap(jsonArr -> Mono.just(
                        // iterate through each element of the prices array and convert into stream
                        StreamSupport.stream(jsonArr.spliterator(), false)
                                .map(item -> {
                                    // custom mapping to Price field to convert data type
                                    // LOG.info(() -> "item: " + item);
                                    float open = item.get("open").floatValue();
                                    float close = item.get("close").floatValue();
                                    float low = item.get("low").floatValue();
                                    float high = item.get("high").floatValue();
                                    int volume = item.get("volume").asInt();
                                    BigDecimal timestamp = BigDecimal.valueOf(item.get("timestamp").doubleValue());
                                    ZonedDateTime datetime;

                                    try {
                                        datetime = ZonedDateTime.parse(item.get("date").asText(),
                                                DateTimeFormatter.ISO_DATE_TIME);
                                    } catch (DateTimeParseException e) {
                                        // couldn't parse to a ZoneDateTime, try LocalDateTime
                                        LocalDateTime dt = LocalDateTime.parse(item.get("date").asText(),
                                                DateTimeFormatter.ISO_DATE_TIME);
                                        // convert to a timezone
                                        datetime = dt.atZone(ZoneId.of("UTC"));
                                    }

                                    return new Price(open, close, low, high, volume, timestamp, datetime);
                                })
                                .toList()))
                .log();
    }

    public Mono<List<Dividend>> getDividends(String stockName) {
        return this.webClient.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder
                            .path("/dividends/{stock}")
                            .build(stockName);
                    LOG.info(() -> LOG_PREFIX_FLASK_SEARCH + uri.toString());
                    return uri;
                })
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.path("dividends"))
                .flatMap(jsonArr -> Mono.just(
                        // iterate through each element of the dividends array and convert into stream
                        StreamSupport.stream(jsonArr.spliterator(), false)
                                .map(item -> {
                                    // custom mapping to Dividend field to convert data type
                                    // LOG.info(() -> "item: " + item);
                                    float value = item.get("value").floatValue();
                                    BigDecimal timestamp = BigDecimal.valueOf(item.get("timestamp").doubleValue());
                                    ZonedDateTime datetime;

                                    try {
                                        datetime = ZonedDateTime.parse(item.get("date").asText(),
                                                DateTimeFormatter.ISO_DATE_TIME);
                                    } catch (DateTimeParseException e) {
                                        // couldn't parse to a ZoneDateTime, try LocalDateTime
                                        LocalDateTime dt = LocalDateTime.parse(item.get("date").asText(),
                                                DateTimeFormatter.ISO_DATE_TIME);
                                        // convert to a timezone
                                        datetime = dt.atZone(ZoneId.of("UTC"));
                                    }

                                    return new Dividend(value, timestamp, datetime);
                                })
                                .toList()))
                .log();
    }

    public Mono<List<Split>> getSplits(String stockName) {
        return this.webClient.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder
                            .path("/splits/{stock}")
                            .build(stockName);
                    LOG.info(() -> LOG_PREFIX_FLASK_SEARCH + uri.toString());
                    return uri;
                })
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.path("splits"))
                .flatMap(jsonArr -> Mono.just(
                        // iterate through each element of the splits array and convert into stream
                        StreamSupport.stream(jsonArr.spliterator(), false)
                                .map(item -> {
                                    // custom mapping to Split field to convert data type
                                    // LOG.info(() -> "item: " + item);
                                    float value = item.get("value").floatValue();
                                    BigDecimal timestamp = BigDecimal.valueOf(item.get("timestamp").doubleValue());
                                    ZonedDateTime datetime;

                                    try {
                                        datetime = ZonedDateTime.parse(item.get("date").asText(),
                                                DateTimeFormatter.ISO_DATE_TIME);
                                    } catch (DateTimeParseException e) {
                                        // couldn't parse to a ZoneDateTime, try LocalDateTime
                                        LocalDateTime dt = LocalDateTime.parse(item.get("date").asText(),
                                                DateTimeFormatter.ISO_DATE_TIME);
                                        // convert to a timezone
                                        datetime = dt.atZone(ZoneId.of("UTC"));
                                    }

                                    return new Split(value, timestamp, datetime);
                                })
                                .toList()))
                .log();
    }
}
