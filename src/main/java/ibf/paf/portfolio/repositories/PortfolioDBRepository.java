package ibf.paf.portfolio.repositories;

import static ibf.paf.portfolio.repositories.SQL.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf.paf.portfolio.models.CreateUserForm;
import ibf.paf.portfolio.models.Portfolio;
import ibf.paf.portfolio.models.ShareCounter;
import ibf.paf.portfolio.models.Stock;
import ibf.paf.portfolio.models.Transaction;
import ibf.paf.portfolio.models.UserProfile;
import ibf.paf.portfolio.models.Watchlist;

@Repository
public class PortfolioDBRepository {
    private static final Logger LOG = Logger.getLogger(PortfolioDBRepository.class.getName());

    // make sure UTC ZoneOffset is formatted as +00:00 instead of Z,
    // which MySQL CONVERT_TZ function does not support
    private DateTimeFormatter offsetFormatter = DateTimeFormatter.ofPattern("xxx");

    @Autowired
    JdbcTemplate template;

    // # Users

    public List<UserProfile> getAllUsers() {
        final List<UserProfile> users = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_USER_SELECT_ALL);

        while (rs.next()) {
            UserProfile userProfile = UserProfile.populate(rs);
            users.add(userProfile);
        }
        return users;
    }

    // TODO return the portfolios and watchlists for the user too
    public Optional<UserProfile> getUserById(final int uId) {
        final SqlRowSet rs = template.queryForRowSet(SQL_USER_SELECT_BY_ID, uId);

        if (rs.next()) {
            return Optional.ofNullable(UserProfile.populate(rs));
        }
        return Optional.empty();
    }

    public int addUser(final CreateUserForm createUserForm) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_USER_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, createUserForm.email());
            ps.setString(2, createUserForm.password());
            ps.setString(3, createUserForm.displayName());
            return ps;
        }, keyHolder);

        try {
            return keyHolder.getKey().intValue();
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    public boolean updateUser(final int uId, final UserProfile userProfile) {
        int updated = template.update(SQL_USER_UPDATE_BY_ID,
                userProfile.email(),
                userProfile.displayName(),
                userProfile.avatarUrl(),
                uId);

        return updated > 0;
    }

    public boolean deleteUser(final int uId) {
        int deleted = template.update(SQL_USER_DELETE_BY_ID, uId);
        return deleted > 0;
    }

    // # Watchlists

    public List<Watchlist> getAllWatchlists(final int uId) {
        final List<Watchlist> watchlists = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_USER_WATCHLIST_SELECT_ALL, uId);

        while (rs.next()) {
            Watchlist watchlist = Watchlist.populate(rs);
            watchlists.add(watchlist);
        }
        return watchlists;
    }

    public Optional<Watchlist> getWatchlistById(final int wId) {
        final SqlRowSet rs = template.queryForRowSet(SQL_USER_WATCHLIST_SELECT_BY_ID, wId);

        if (rs.next()) {
            return Optional.ofNullable(Watchlist.populate(rs));
        }
        return Optional.empty();
    }

    // the resource path uId should be respected, instead of reading the value from
    // the request body
    public int addWatchlist(final int uId, final Watchlist watchlist) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_USER_WATCHLIST_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, watchlist.getwName());
            ps.setInt(2, uId);
            return ps;
        }, keyHolder);

        try {
            return keyHolder.getKey().intValue();
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    public boolean updateWatchlist(final int wId, final Watchlist watchlist) {
        int updated = template.update(SQL_WATCHLIST_UPDATE_BY_ID,
                watchlist.getwName(),
                wId);

        return updated > 0;
    }

    public boolean deleteWatchlist(final int wId) {
        int deleted = template.update(SQL_WATCHLIST_DELETE_BY_ID, wId);
        return deleted > 0;
    }

    // # Watchlist Stocks

    public List<ShareCounter> getAllWatchlistStocks(final int wId) {
        final List<ShareCounter> watchStocks = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_USER_WATCHLIST_STOCK_SELECT_ALL, wId);

        while (rs.next()) {
            ShareCounter watchStock = ShareCounter.populate(rs);
            watchStocks.add(watchStock);
        }
        return watchStocks;
    }

    public Optional<ShareCounter> getWatchlistStockById(final int wsId) {
        final SqlRowSet rs = template.queryForRowSet(SQL_USER_WATCHLIST_STOCK_SELECT_BY_ID, wsId);

        if (rs.next()) {
            return Optional.ofNullable(ShareCounter.populate(rs));
        }
        return Optional.empty();
    }

    // the resource path wId should be respected, instead of reading the value from
    // the request body
    public int addWatchlistStock(final int wId, final ShareCounter watchStock) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_USER_WATCHLIST_STOCK_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, watchStock.getSymbol());
            ps.setInt(2, wId);
            return ps;
        }, keyHolder);

        try {
            return keyHolder.getKey().intValue();
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    public boolean deleteWatchlistStock(final int wsId) {
        int deleted = template.update(SQL_WATCHLIST_STOCK_DELETE_BY_ID, wsId);
        return deleted > 0;
    }

    // # Portfolios

    public List<Portfolio> getAllPortfolios(final int uId) {
        final List<Portfolio> portfolios = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_USER_PORTFOLIO_SELECT_ALL, uId);

        while (rs.next()) {
            Portfolio portfolio = Portfolio.populate(rs);
            portfolios.add(portfolio);
        }
        return portfolios;
    }

    public Optional<Portfolio> getPortfolioById(final int pId) {
        final SqlRowSet rs = template.queryForRowSet(SQL_USER_PORTFOLIO_SELECT_BY_ID, pId);

        if (rs.next()) {
            return Optional.ofNullable(Portfolio.populate(rs));
        }
        return Optional.empty();
    }

    // the resource path uId should be respected, instead of reading the value from
    // the request body
    public int addPortfolio(final int uId, final Portfolio portfolio) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_USER_PORTFOLIO_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, portfolio.getpName());
            ps.setInt(2, uId);
            return ps;
        }, keyHolder);

        try {
            return keyHolder.getKey().intValue();
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    public boolean updatePortfolio(final int pId, final Portfolio portfolio) {
        int updated = template.update(SQL_PORTFOLIO_UPDATE_BY_ID,
                portfolio.getpName(),
                pId);

        return updated > 0;
    }

    public boolean deletePortfolio(final int pId) {
        int deleted = template.update(SQL_PORTFOLIO_DELETE_BY_ID, pId);
        return deleted > 0;
    }

    // # Portfolio Stocks

    public List<Stock> getAllPortfolioStocks(final int pId) {
        final List<Stock> portfolioStocks = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_USER_PORTFOLIO_STOCK_SELECT_ALL, pId);

        while (rs.next()) {
            Stock portfolioStock = Stock.create(rs);
            portfolioStocks.add(portfolioStock);
        }
        return portfolioStocks;
    }

    public Optional<Stock> getPortfolioStockById(final int psId) {
        final SqlRowSet rs = template.queryForRowSet(SQL_USER_PORTFOLIO_STOCK_SELECT_BY_ID, psId);

        if (rs.next()) {
            return Optional.ofNullable(Stock.create(rs));
        }
        return Optional.empty();
    }

    // the resource path pId should be respected, instead of reading the value from
    // the request body
    public int addPortfolioStock(final int pId, final Stock portfolioStock) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_USER_PORTFOLIO_STOCK_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, portfolioStock.getSymbol());
            ps.setInt(2, pId);
            return ps;
        }, keyHolder);

        try {
            return keyHolder.getKey().intValue();
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    public boolean deletePortfolioStock(final int psId) {
        int deleted = template.update(SQL_PORTFOLIO_STOCK_DELETE_BY_ID, psId);
        return deleted > 0;
    }

    // # Portfolio Stock Transactions

    public List<Transaction> getAllPortfolioStockTransactions(final int psId) {
        final List<Transaction> transactions = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_PORTFOLIO_STOCK_TRANSACTION_SELECT_ALL, psId);

        while (rs.next()) {
            Optional<Transaction> opt = Transaction.create(rs);
            if (opt.isPresent()) {
                transactions.add(opt.get());
            }
        }
        return transactions;
    }

    public Optional<Transaction> getPortfolioStockTransactionById(final int pstId) {
        final SqlRowSet rs = template.queryForRowSet(SQL_PORTFOLIO_STOCK_TRANSACTION_SELECT_BY_ID, pstId);

        if (rs.next()) {
            return Transaction.create(rs);
        }
        return Optional.empty();
    }

    // the resource path psId should be respected, instead of reading the value from
    // the request body
    public int addPortfolioStockTransaction(final int psId, final Transaction transaction) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        LOG.info(SQL_PORTFOLIO_STOCK_TRANSACTION_INSERT);
        LOG.info(transaction.datetime().getOffset().toString());

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_PORTFOLIO_STOCK_TRANSACTION_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setFloat(1, transaction.quantity());
            ps.setFloat(2, transaction.price());
            ps.setFloat(3, transaction.brokerageFees());
            ps.setString(4, transaction.datetime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setString(5, offsetFormatter.format(transaction.datetime().getOffset()));
            ps.setInt(6, psId);

            return ps;
        }, keyHolder);

        try {
            return keyHolder.getKey().intValue();
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    public boolean updatePortfolioStockTransaction(final int pstId, final Transaction transaction) {
        int updated = template.update(SQL_PORTFOLIO_STOCK_TRANSACTION_UPDATE_BY_ID,
                transaction.quantity(),
                transaction.price(),
                transaction.brokerageFees(),
                transaction.datetime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                offsetFormatter.format(transaction.datetime().getOffset()),
                pstId);
        return updated > 0;
    }

    public boolean deletePortfolioStockTransaction(final int pstId) {
        int deleted = template.update(SQL_PORTFOLIO_STOCK_TRANSACTION_DELETE_BY_ID, pstId);
        return deleted > 0;
    }
}
