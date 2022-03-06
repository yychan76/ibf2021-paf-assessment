package ibf.paf.portfolio.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.paf.portfolio.models.Portfolio;
import ibf.paf.portfolio.models.Stock;
import ibf.paf.portfolio.models.Transaction;
import ibf.paf.portfolio.repositories.PortfolioDBRepository;

@Service
public class PortfolioService {
    private static final Logger LOG = Logger.getLogger(PortfolioService.class.getName());

    @Autowired
    private PortfolioDBRepository portfolioRepo;

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
        return portfolioRepo.getAllPortfolioStocks(pId);
    }

    public Optional<Stock> getPortfolioStockById(final int psId) {
        return portfolioRepo.getPortfolioStockById(psId);
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
