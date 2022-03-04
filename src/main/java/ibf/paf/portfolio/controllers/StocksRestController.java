package ibf.paf.portfolio.controllers;

import java.util.Collections;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ibf.paf.portfolio.models.QuerySymbolsResult;
import ibf.paf.portfolio.services.SymbolSearchService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/stocks", produces = { MediaType.APPLICATION_JSON_VALUE })
public class StocksRestController {
    private static final Logger LOG = Logger.getLogger(StocksRestController.class.getName());

    @Autowired
    SymbolSearchService searchService;

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
}
