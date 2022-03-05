package ibf.paf.portfolio.controllers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ibf.paf.portfolio.models.Price;
import ibf.paf.portfolio.models.QuerySymbolsResult;
import ibf.paf.portfolio.services.StockDataService;
import ibf.paf.portfolio.services.SymbolSearchService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/stocks", produces = { MediaType.APPLICATION_JSON_VALUE })
public class StocksRestController {
    private static final Logger LOG = Logger.getLogger(StocksRestController.class.getName());

    @Autowired
    SymbolSearchService searchService;

    @Autowired
    StockDataService dataService;

    @GetMapping("query")
    public Mono<QuerySymbolsResult> getSymbols(@RequestParam(required = false) String keywords) {
        LOG.info(() -> "Keywords: " + keywords);
        return searchService.searchSymbols(keywords)
                .map(result -> {
                    LOG.info(() -> "Symbol search result: " + result.toString());
                    return result.isEmpty() ? new QuerySymbolsResult(Collections.emptyList())
                            : new QuerySymbolsResult(result);
                });
    }

    @GetMapping("info/{stockName}")
    public Mono<String> getStockInfo(@PathVariable String stockName) {
        // just passthrough the results from the flask query without going through a
        // POJO
        return dataService.passThroughStockInfo(stockName);
    }

    @GetMapping("prices/{stockName}")
    public Mono<List<Price>> getPrices(@PathVariable String stockName,
            @RequestParam(required = false) String period,
            @RequestParam(defaultValue = "1d") String interval,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {
        final String LOG_PREFIX_PRICE_SEARCH = "Price search result: ";
        if (start != null) {
            if (end != null) {
                return dataService.getPrices(stockName, LocalDate.parse(start), LocalDate.parse(end), interval)
                        .doOnNext(result -> LOG.info(() -> LOG_PREFIX_PRICE_SEARCH + result.toString()));
            } else {
                return dataService.getPrices(stockName, LocalDate.parse(start), interval)
                        .doOnNext(result -> LOG.info(() -> LOG_PREFIX_PRICE_SEARCH + result.toString()));
            }
        } else {
            return dataService.getPrices(stockName, (period != null) ? period : "max", interval)
                    .doOnNext(result -> LOG.info(() -> LOG_PREFIX_PRICE_SEARCH + result.toString()));
        }
    }
}
