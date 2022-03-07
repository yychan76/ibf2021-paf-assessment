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

import ibf.paf.portfolio.models.Dividend;
import ibf.paf.portfolio.models.Price;
import ibf.paf.portfolio.models.ShareCounter;
import ibf.paf.portfolio.models.Split;
import ibf.paf.portfolio.models.Watchlist;
import ibf.paf.portfolio.repositories.PortfolioDBRepository;

@Service
public class WatchlistService {
    private static final Logger LOG = Logger.getLogger(WatchlistService.class.getName());
    // need to add the jackson-datatype-jsr310 module to support ZonedDateTime
    // serialization
    // also need to provide ZonedDateTimeSerializer in the Dividend, Price, Split POJO
    private JsonMapper jsonMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Autowired
    private PortfolioDBRepository portfolioRepo;

    @Autowired
    private StockDataService stockDataSvc;

    // # Watchlists

    public List<Watchlist> getAllWatchlists(final int uId) {
        return portfolioRepo.getAllWatchlists(uId);
    }

    public Optional<Watchlist> getWatchlistById(final int wId) {
        return portfolioRepo.getWatchlistById(wId);
    }

    public int addWatchlist(final int uId, final Watchlist watchlist) throws SQLException {
        return portfolioRepo.addWatchlist(uId, watchlist);
    }

    public boolean updateWatchlist(final int wId, final Watchlist watchlist) {
        return portfolioRepo.updateWatchlist(wId, watchlist);
    }

    public boolean deleteWatchlist(final int wId) {
        return portfolioRepo.deleteWatchlist(wId);
    }

    // # Watchlist Stocks

    public List<ShareCounter> getAllWatchlistStocks(final int wId) {
        // return portfolioRepo.getAllWatchlistStocks(wId);
        List<ShareCounter> counters = portfolioRepo.getAllWatchlistStocks(wId);
        return counters.stream()
            .map(item -> {
                String symbol = item.getSymbol();
                String stockInfoStr = stockDataSvc.passThroughStockInfo(symbol).block();
                // List<Dividend> dividends = stockDataSvc.getDividends(symbol).block();
                // List<Split> splits = stockDataSvc.getSplits(symbol).block();
                // List<Price> prices = stockDataSvc.getPrices(symbol).block();

                try {
                    JsonNode infoNode = jsonMapper.readTree(stockInfoStr);
                    // LOG.info(infoNode.toString());
                    // LOG.info("currentPrice:" + infoNode.get("currentPrice").floatValue());

                    item.setCurrentPrice(infoNode.get("currentPrice") != null ? (float)infoNode.get("currentPrice").asDouble() : 0);
                    item.setPreviousClose(infoNode.get("previousClose") != null ? (float)infoNode.get("previousClose").asDouble() : 0);
                    item.setCurrency(infoNode.get("currency") != null ? infoNode.get("currency").asText() : "");
                    item.setRegion(infoNode.get("country") != null ? infoNode.get("country").asText() : "");
                    item.setName(infoNode.get("longName") != null ? infoNode.get("longName").asText() : "");
                    item.setType(infoNode.get("quoteType") != null ? infoNode.get("quoteType").asText() : "");
                    // item.setDividends(dividends);
                    // item.setSplits(splits);
                    // item.setPrices(prices);
                } catch (JsonProcessingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return item;
            }).toList();
    }

    public Optional<ShareCounter> getWatchlistStockById(final int wsId) {
        return portfolioRepo.getWatchlistStockById(wsId);
    }

    public int addWatchlistStock(final int wId, final ShareCounter shareCounter) throws SQLException {
        return portfolioRepo.addWatchlistStock(wId, shareCounter);
    }

    public boolean deleteWatchlistStock(final int wsId) {
        return portfolioRepo.deleteWatchlistStock(wsId);
    }
}
