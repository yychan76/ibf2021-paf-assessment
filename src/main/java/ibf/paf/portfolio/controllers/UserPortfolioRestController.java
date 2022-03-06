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

import ibf.paf.portfolio.models.Portfolio;
import ibf.paf.portfolio.models.Stock;
import ibf.paf.portfolio.models.Transaction;
import ibf.paf.portfolio.services.PortfolioService;

@RestController
@RequestMapping(path = "/api/users/{uId}/portfolios", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserPortfolioRestController {
    private static final Logger LOG = Logger.getLogger(UserPortfolioRestController.class.getName());
    // need to add the jackson-datatype-jsr310 module to support ZonedDateTime
    // serialization
    // also need to provide ZonedDateTimeSerializer in the Transaction POJO
    private JsonMapper jsonMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Autowired
    private PortfolioService portfolioSvc;

    // # Portfolios

    @GetMapping
    public ResponseEntity<String> getUserPortfolios(@PathVariable int uId) {
        LOG.info(() -> "getUserPortfolios for uId: " + uId);

        List<Portfolio> portfolios = portfolioSvc.getAllPortfolios(uId);
        LOG.info(() -> "getUserPortfolios() returned: " + portfolios.toString());
        try {
            return ResponseEntity.ok(jsonMapper.writeValueAsString(portfolios));
        } catch (IOException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("{pId}")
    public ResponseEntity<String> getPortfolioById(@PathVariable int pId) throws JsonProcessingException {
        Optional<Portfolio> opt = portfolioSvc.getPortfolioById(pId);
        if (opt.isPresent()) {
            LOG.info(() -> "getPortfolioById() returned: " + opt.get().toString());
            return ResponseEntity.ok(jsonMapper.writeValueAsString(opt.get()));
        } else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().build().toUri();
            return ResponseEntity.notFound().location(location).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCreatePortfolio(@PathVariable int uId, @RequestBody Portfolio portfolio)
            throws JsonProcessingException {
        LOG.info(() -> "portfolio: " + portfolio.toString());
        try {
            int pId = portfolioSvc.addPortfolio(uId, portfolio);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(pId).toUri();
            return ResponseEntity.created(location).body(jsonMapper
                    .writeValueAsString(Collections.singletonMap("response", "Portfolio: %d created".formatted(pId))));
        } catch (SQLException | DataIntegrityViolationException e) {
            return ResponseEntity.unprocessableEntity()
                    .body(jsonMapper.writeValueAsString(Collections.singletonMap("error", e.getMessage())));
        }

    }

    @PutMapping(path = "{pId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePortfolio(@PathVariable int pId, @RequestBody Portfolio portfolio)
            throws JsonProcessingException {
        LOG.info(() -> "updatePortfolio: " + pId + " " + portfolio.toString());
        boolean updated = portfolioSvc.updatePortfolio(pId, portfolio);
        if (updated) {
            return ResponseEntity.ok().body(jsonMapper
                    .writeValueAsString(
                            (Collections.singletonMap("response", "Portfolio: %d updated".formatted(pId)))));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonMapper
                    .writeValueAsString(Collections.singletonMap("error", "Portfolio: %d update fail".formatted(pId))));
        }
    }

    @DeleteMapping("{pId}")
    public ResponseEntity<String> deletePortfolio(@PathVariable int pId) throws JsonProcessingException {
        LOG.info(() -> "deletePortfolio: " + pId);
        boolean deleted = portfolioSvc.deletePortfolio(pId);
        if (deleted) {
            return ResponseEntity.ok(jsonMapper
                    .writeValueAsString(Collections.singletonMap("response", "Portfolio: %d deleted".formatted(pId))));
        } else {
            return ResponseEntity.internalServerError().body(jsonMapper
                    .writeValueAsString(Collections.singletonMap("error", "Portfolio: %d delete fail".formatted(pId))));
        }
    }

    // # Portfolio Stocks

    @GetMapping("{pId}/stocks")
    public ResponseEntity<String> getUserPortfolioStocks(@PathVariable int uId, @PathVariable int pId) {
        LOG.info(() -> "getUserPortfolioStocks for uId: " + uId + " pId: " + pId);

        List<Stock> portfolioStocks = portfolioSvc.getAllPortfolioStocks(pId);
        LOG.info(() -> "getUserPortfolioStocks() returned: " + portfolioStocks.toString());
        try {
            return ResponseEntity.ok(jsonMapper.writeValueAsString(portfolioStocks));
        } catch (IOException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("{pId}/stocks/{psId}")
    public ResponseEntity<String> getPortfolioStockById(@PathVariable int pId, @PathVariable int psId)
            throws JsonProcessingException {
        Optional<Stock> opt = portfolioSvc.getPortfolioStockById(psId);
        if (opt.isPresent()) {
            LOG.info(() -> "getPortfolioStockById() returned: " + opt.get().toString());
            return ResponseEntity.ok(jsonMapper.writeValueAsString(opt.get()));
        } else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().build().toUri();
            return ResponseEntity.notFound().location(location).build();
        }
    }

    @PostMapping(path = "{pId}/stocks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addPortfolioStock(@PathVariable int pId, @RequestBody Stock portfolioStock)
            throws JsonProcessingException {
        LOG.info(() -> "portfolio stock: " + portfolioStock.toString());
        try {
            int psId = portfolioSvc.addPortfolioStock(pId, portfolioStock);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(psId).toUri();
            return ResponseEntity.created(location).body(jsonMapper
                    .writeValueAsString(
                            Collections.singletonMap("response", "Portfolio Stock: %d added".formatted(psId))));
        } catch (SQLException | DataIntegrityViolationException e) {
            return ResponseEntity.unprocessableEntity()
                    .body(jsonMapper.writeValueAsString(Collections.singletonMap("error", e.getMessage())));
        }

    }

    @DeleteMapping("{pId}/stocks/{psId}")
    public ResponseEntity<String> deletePortfolioStock(@PathVariable int psId) throws JsonProcessingException {
        LOG.info(() -> "deletePortfolioStock: " + psId);
        boolean deleted = portfolioSvc.deletePortfolioStock(psId);
        if (deleted) {
            return ResponseEntity.ok(jsonMapper
                    .writeValueAsString(
                            Collections.singletonMap("response", "Portfolio Stock: %d deleted".formatted(psId))));
        } else {
            return ResponseEntity.internalServerError().body(jsonMapper
                    .writeValueAsString(
                            Collections.singletonMap("error", "Portfolio Stock: %d delete fail".formatted(psId))));
        }
    }

    // # Portfolio Stock Transactions

    @GetMapping("{pId}/stocks/{psId}/transactions")
    public ResponseEntity<String> getUserPortfolioStockTransactions(@PathVariable int uId, @PathVariable int psId) {
        LOG.info(() -> "getUserPortfolioStockTransactions for uId: " + uId + " psId: " + psId);

        List<Transaction> transactions = portfolioSvc.getAllPortfolioStockTransactions(psId);
        LOG.info(() -> "getUserPortfolioStockTransactions() returned: " + transactions.toString());
        try {
            return ResponseEntity.ok(jsonMapper.writeValueAsString(transactions));
        } catch (IOException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("{pId}/stocks/{psId}/transactions/{pstId}")
    public ResponseEntity<String> getPortfolioStocTransactionkById(@PathVariable int psId, @PathVariable int pstId)
            throws JsonProcessingException {
        Optional<Transaction> opt = portfolioSvc.getPortfolioStockTransactionById(pstId);
        if (opt.isPresent()) {
            LOG.info(() -> "getPortfolioStocTransactionkById() returned: " + opt.get().toString());
            return ResponseEntity.ok(jsonMapper.writeValueAsString(opt.get()));
        } else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().build().toUri();
            return ResponseEntity.notFound().location(location).build();
        }
    }

    @PostMapping(path = "{pId}/stocks/{psId}/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addPortfolioStockTransaction(@PathVariable int psId,
            @RequestBody Transaction transaction)
            throws JsonProcessingException {
        LOG.info(() -> "transaction: " + transaction.toString());
        try {
            int pstId = portfolioSvc.addPortfolioStockTransaction(psId, transaction);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(pstId).toUri();
            return ResponseEntity.created(location).body(jsonMapper
                    .writeValueAsString(
                            Collections.singletonMap("response",
                                    "Portfolio Stock Transaction: %d added".formatted(pstId))));
        } catch (SQLException | DataIntegrityViolationException e) {
            return ResponseEntity.unprocessableEntity()
                    .body(jsonMapper.writeValueAsString(Collections.singletonMap("error", e.getMessage())));
        }

    }

    @PutMapping(path = "{pId}/stocks/{psId}/transactions/{pstId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePortfolioStockTransaction(@PathVariable int pstId,
            @RequestBody Transaction transaction)
            throws JsonProcessingException {
        LOG.info(() -> "updatePortfolioStockTransaction: " + pstId + " " + transaction.toString());
        boolean updated = portfolioSvc.updatePortfolioStockTransaction(pstId, transaction);
        if (updated) {
            return ResponseEntity.ok().body(jsonMapper
                    .writeValueAsString(
                            (Collections.singletonMap("response",
                                    "Portfolio Stock Transaction: %d updated".formatted(pstId)))));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonMapper
                    .writeValueAsString(Collections.singletonMap("error",
                            "Portfolio Stock Transaction: %d update fail".formatted(pstId))));
        }
    }

    @DeleteMapping("{pId}/stocks/{psId}/transactions/{pstId}")
    public ResponseEntity<String> deletePortfolioStockTransaction(@PathVariable int pstId)
            throws JsonProcessingException {
        LOG.info(() -> "deletePortfolioStockTransaction: " + pstId);
        boolean deleted = portfolioSvc.deletePortfolioStockTransaction(pstId);
        if (deleted) {
            return ResponseEntity.ok(jsonMapper
                    .writeValueAsString(
                            Collections.singletonMap("response",
                                    "Portfolio Stock Transaction: %d deleted".formatted(pstId))));
        } else {
            return ResponseEntity.internalServerError().body(jsonMapper
                    .writeValueAsString(
                            Collections.singletonMap("error",
                                    "Portfolio Stock Transaction: %d delete fail".formatted(pstId))));
        }
    }

}
