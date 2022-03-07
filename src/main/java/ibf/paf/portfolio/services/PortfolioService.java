package ibf.paf.portfolio.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.paf.portfolio.models.Portfolio;
import ibf.paf.portfolio.models.Stock;
import ibf.paf.portfolio.models.Transaction;
import ibf.paf.portfolio.repositories.PortfolioDBRepository;

@Service
public class PortfolioService {
    private static final Logger LOG = Logger.getLogger(PortfolioService.class.getName());
    // need to add the jackson-datatype-jsr310 module to support ZonedDateTime
    // serialization
    // also need to provide ZonedDateTimeSerializer in the Dividend, Price, Split
    // POJO
    private JsonMapper jsonMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Autowired
    private PortfolioDBRepository portfolioRepo;

    @Autowired
    private StockDataService stockDataSvc;

    // # Portfolios

    public List<Portfolio> getAllPortfolios(final int uId) {
        return portfolioRepo.getAllPortfolios(uId);
    }

    public Optional<Portfolio> getPortfolioById(final int pId) {
        return portfolioRepo.getPortfolioById(pId);
    }

    public int addPortfolio(final int uId, final Portfolio portfolio) throws SQLException {
        return portfolioRepo.addPortfolio(uId, portfolio);
    }

    public boolean updatePortfolio(final int pId, final Portfolio portfolio) {
        return portfolioRepo.updatePortfolio(pId, portfolio);
    }

    public boolean deletePortfolio(final int pId) {
        return portfolioRepo.deletePortfolio(pId);
    }

    // # Portfolio Stocks

    public List<Stock> getAllPortfolioStocks(final int pId) {
        // return portfolioRepo.getAllPortfolioStocks(pId);
        List<Stock> stocks = portfolioRepo.getAllPortfolioStocks(pId);
        return stocks.stream()
                .map(item -> {
                    return fillInStockInfo(item);
                }).toList();
    }

    public Optional<Stock> getPortfolioStockById(final int psId) {
        Optional<Stock> opt = portfolioRepo.getPortfolioStockById(psId);
        if (opt.isPresent()) {
            Stock item = opt.get();
            return Optional.of(fillInStockInfo(item));
        }
        return Optional.empty();
    }

    private Stock fillInStockInfo(Stock stock) {
        String symbol = stock.getSymbol();
        String stockInfoStr = stockDataSvc.passThroughStockInfo(symbol).block();
        // List<Dividend> dividends = stockDataSvc.getDividends(symbol).block();
        // List<Split> splits = stockDataSvc.getSplits(symbol).block();
        // List<Price> prices = stockDataSvc.getPrices(symbol).block();

        try {
            JsonNode infoNode = jsonMapper.readTree(stockInfoStr);
            // LOG.info(infoNode.toString());
            // LOG.info("currentPrice:" + infoNode.get("currentPrice").floatValue());

            stock.setCurrentPrice(
                    infoNode.get("currentPrice") != null ? (float) infoNode.get("currentPrice").asDouble() : 0);
            stock.setPreviousClose(
                    infoNode.get("previousClose") != null ? (float) infoNode.get("previousClose").asDouble() : 0);
            stock.setCurrency(infoNode.get("currency") != null ? infoNode.get("currency").asText() : "");
            stock.setRegion(infoNode.get("country") != null ? infoNode.get("country").asText() : "");
            stock.setName(infoNode.get("longName") != null ? infoNode.get("longName").asText() : "");
            stock.setType(infoNode.get("quoteType") != null ? infoNode.get("quoteType").asText() : "");
            // stock.setDividends(dividends);
            // stock.setSplits(splits);
            // stock.setPrices(prices);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stock;
    }

    public int addPortfolioStock(final int pId, final Stock stock) throws SQLException {
        return portfolioRepo.addPortfolioStock(pId, stock);
    }

    public boolean deletePortfolioStock(final int psId) {
        return portfolioRepo.deletePortfolioStock(psId);
    }

    // # Portfolio Stock Transactions

    public List<Transaction> getAllPortfolioStockTransactions(final int psId) {
        return portfolioRepo.getAllPortfolioStockTransactions(psId);
    }

    public Optional<Transaction> getPortfolioStockTransactionById(final int pstId) {
        return portfolioRepo.getPortfolioStockTransactionById(pstId);
    }

    public int addPortfolioStockTransaction(final int psId, final Transaction transaction) throws SQLException {
        return portfolioRepo.addPortfolioStockTransaction(psId, transaction);
    }

    public boolean updatePortfolioStockTransaction(final int pstId, final Transaction transaction) {
        return portfolioRepo.updatePortfolioStockTransaction(pstId, transaction);
    }

    public boolean deletePortfolioStockTransaction(final int pstId) {
        return portfolioRepo.deletePortfolioStockTransaction(pstId);
    }
}
