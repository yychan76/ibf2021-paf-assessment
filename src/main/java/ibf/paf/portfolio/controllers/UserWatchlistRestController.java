package ibf.paf.portfolio.controllers;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ibf.paf.portfolio.models.ShareCounter;
import ibf.paf.portfolio.models.Watchlist;
import ibf.paf.portfolio.services.WatchlistService;

@RestController
@RequestMapping(path = "/api/users/{uId}/watchlists", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserWatchlistRestController {
    private static final Logger LOG = Logger.getLogger(UserWatchlistRestController.class.getName());
    private JsonMapper jsonMapper = new JsonMapper();

    @Autowired
    private WatchlistService watchlistSvc;

    // # Watchlists

    @GetMapping
    public ResponseEntity<String> getUserWatchlists(@PathVariable int uId) {
        LOG.info(() -> "getUserWatchlists for uId: " + uId);

        List<Watchlist> watchlists = watchlistSvc.getAllWatchlists(uId);
        LOG.info(() -> "getUserWatchlists() returned: " + watchlists.toString());
        try {
            return ResponseEntity.ok(jsonMapper.writeValueAsString(watchlists));
        } catch (IOException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("{wId}")
    public ResponseEntity<String> getWatchlistById(@PathVariable int wId) throws JsonProcessingException {
        Optional<Watchlist> opt = watchlistSvc.getWatchlistById(wId);
        if (opt.isPresent()) {
            LOG.info(() -> "getWatchlistById() returned: " + opt.get().toString());
            return ResponseEntity.ok(jsonMapper.writeValueAsString(opt.get()));
        } else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().build().toUri();
            return ResponseEntity.notFound().location(location).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCreateWatchlist(@PathVariable int uId, @RequestBody Watchlist watchlist)
            throws JsonProcessingException {
        LOG.info(() -> "watchlist: " + watchlist.toString());
        try {
            int wId = watchlistSvc.addWatchlist(uId, watchlist);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(wId).toUri();
            return ResponseEntity.created(location).body(jsonMapper
                    .writeValueAsString(Collections.singletonMap("response", "Watchlist: %d created".formatted(wId))));
        } catch (SQLException | DataIntegrityViolationException e) {
            return ResponseEntity.unprocessableEntity()
                    .body(jsonMapper.writeValueAsString(Collections.singletonMap("error", e.getMessage())));
        }

    }

    @PutMapping(path = "{wId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateWatchlist(@PathVariable int wId, @RequestBody Watchlist watchlist)
            throws JsonProcessingException {
        LOG.info(() -> "updateWatchlist: " + wId + " " + watchlist.toString());
        boolean updated = watchlistSvc.updateWatchlist(wId, watchlist);
        if (updated) {
            return ResponseEntity.ok().body(jsonMapper
                    .writeValueAsString(
                            (Collections.singletonMap("response", "Watchlist: %d updated".formatted(wId)))));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonMapper
                    .writeValueAsString(Collections.singletonMap("error", "Watchlist: %d update fail".formatted(wId))));
        }
    }

    @DeleteMapping("{wId}")
    public ResponseEntity<String> deleteWatchlist(@PathVariable int wId) throws JsonProcessingException {
        LOG.info(() -> "deleteWatchlist: " + wId);
        boolean deleted = watchlistSvc.deleteWatchlist(wId);
        if (deleted) {
            return ResponseEntity.ok(jsonMapper
                    .writeValueAsString(Collections.singletonMap("response", "Watchlist: %d deleted".formatted(wId))));
        } else {
            return ResponseEntity.internalServerError().body(jsonMapper
                    .writeValueAsString(Collections.singletonMap("error", "Watchlist: %d delete fail".formatted(wId))));
        }
    }

    // # Watchlist Stocks

    @GetMapping("{wId}/stocks")
    public ResponseEntity<String> getUserWatchlistStocks(@PathVariable int uId, @PathVariable int wId) {
        LOG.info(() -> "getUserWatchlistStocks for uId: " + uId + " wId: " + wId);

        List<ShareCounter> watchStocks = watchlistSvc.getAllWatchlistStocks(wId);
        LOG.info(() -> "getUserWatchlistStocks() returned: " + watchStocks.toString());
        try {
            return ResponseEntity.ok(jsonMapper.writeValueAsString(watchStocks));
        } catch (IOException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("{wId}/stocks/{wsId}")
    public ResponseEntity<String> getWatchlistStockById(@PathVariable int wId, @PathVariable int wsId)
            throws JsonProcessingException {
        Optional<ShareCounter> opt = watchlistSvc.getWatchlistStockById(wsId);
        if (opt.isPresent()) {
            LOG.info(() -> "getWatchlistStockById() returned: " + opt.get().toString());
            return ResponseEntity.ok(jsonMapper.writeValueAsString(opt.get()));
        } else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().build().toUri();
            return ResponseEntity.notFound().location(location).build();
        }
    }

    @PostMapping(path = "{wId}/stocks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addWatchlistStock(@PathVariable int wId, @RequestBody ShareCounter watchStock)
            throws JsonProcessingException {
        LOG.info(() -> "watchlist stock: " + watchStock.toString());
        try {
            int wsId = watchlistSvc.addWatchlistStock(wId, watchStock);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(wsId).toUri();
            return ResponseEntity.created(location).body(jsonMapper
                    .writeValueAsString(
                            Collections.singletonMap("response", "Watchlist Stock: %d added".formatted(wsId))));
        } catch (SQLException | DataIntegrityViolationException e) {
            return ResponseEntity.unprocessableEntity()
                    .body(jsonMapper.writeValueAsString(Collections.singletonMap("error", e.getMessage())));
        }

    }

    @DeleteMapping("{wId}/stocks/{wsId}")
    public ResponseEntity<String> deleteWatchlistStock(@PathVariable int wsId) throws JsonProcessingException {
        LOG.info(() -> "deleteWatchlistStock: " + wsId);
        boolean deleted = watchlistSvc.deleteWatchlistStock(wsId);
        if (deleted) {
            return ResponseEntity.ok(jsonMapper
                    .writeValueAsString(
                            Collections.singletonMap("response", "Watchlist Stock: %d deleted".formatted(wsId))));
        } else {
            return ResponseEntity.internalServerError().body(jsonMapper
                    .writeValueAsString(
                            Collections.singletonMap("error", "Watchlist Stock: %d delete fail".formatted(wsId))));
        }
    }

}
