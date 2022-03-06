package ibf.paf.portfolio.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.paf.portfolio.models.ShareCounter;
import ibf.paf.portfolio.models.Watchlist;
import ibf.paf.portfolio.repositories.PortfolioDBRepository;

@Service
public class WatchlistService {
    private static final Logger LOG = Logger.getLogger(WatchlistService.class.getName());

    @Autowired
    private PortfolioDBRepository portfolioRepo;

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
        return portfolioRepo.getAllWatchlistStocks(wId);
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
