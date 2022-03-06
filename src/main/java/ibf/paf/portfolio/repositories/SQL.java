package ibf.paf.portfolio.repositories;

public class SQL {

    private SQL() {
        throw new IllegalStateException("Utility class");
    }

    private static final String DATE_FORMAT_STR = "%Y-%m-%dT%T";
    public static final String SQL_USER_SELECT_ALL = "SELECT * FROM user";
    public static final String SQL_USER_SELECT_BY_ID = "SELECT * FROM user WHERE u_id = ?";
    public static final String SQL_USER_INSERT = "INSERT INTO user (email, pw_hash, display_name) VALUES (?, ?, ?)";
    public static final String SQL_USER_UPDATE_BY_ID = "UPDATE user SET email = ?, display_name = ?, avatar_url = ? WHERE u_id = ?";
    public static final String SQL_USER_DELETE_BY_ID = "DELETE FROM user WHERE u_id = ?";
    public static final String SQL_USER_WATCHLIST_SELECT_ALL = "SELECT * FROM watchlist WHERE u_id = ?";
    public static final String SQL_USER_WATCHLIST_SELECT_BY_ID = "SELECT * FROM watchlist WHERE w_id = ?";
    public static final String SQL_USER_WATCHLIST_INSERT = "INSERT INTO watchlist (w_name, u_id) VALUES (?, ?)";
    public static final String SQL_WATCHLIST_UPDATE_BY_ID = "UPDATE watchlist SET w_name = ? WHERE w_id = ?";
    public static final String SQL_WATCHLIST_DELETE_BY_ID = "DELETE FROM watchlist WHERE w_id = ?";
    public static final String SQL_USER_WATCHLIST_STOCK_SELECT_ALL = "SELECT * FROM watchlist_stock WHERE w_id = ?";
    public static final String SQL_USER_WATCHLIST_STOCK_SELECT_BY_ID = "SELECT * FROM watchlist_stock WHERE ws_id = ?";
    public static final String SQL_USER_WATCHLIST_STOCK_INSERT = "INSERT INTO watchlist_stock (symbol, w_id) VALUES (?, ?)";
    public static final String SQL_WATCHLIST_STOCK_DELETE_BY_ID = "DELETE FROM watchlist_stock WHERE ws_id = ?";
    public static final String SQL_USER_PORTFOLIO_SELECT_ALL = "SELECT * FROM portfolio WHERE u_id = ?";
    public static final String SQL_USER_PORTFOLIO_SELECT_BY_ID = "SELECT * FROM portfolio WHERE p_id = ?";
    public static final String SQL_USER_PORTFOLIO_INSERT = "INSERT INTO portfolio (p_name, u_id) VALUES (?, ?)";
    public static final String SQL_PORTFOLIO_UPDATE_BY_ID = "UPDATE portfolio SET p_name = ? WHERE p_id = ?";
    public static final String SQL_PORTFOLIO_DELETE_BY_ID = "DELETE FROM portfolio WHERE p_id = ?";
    public static final String SQL_USER_PORTFOLIO_STOCK_SELECT_ALL = "SELECT * FROM portfolio_stock WHERE p_id = ?";
    public static final String SQL_USER_PORTFOLIO_STOCK_SELECT_BY_ID = "SELECT * FROM portfolio_stock WHERE ps_id = ?";
    public static final String SQL_USER_PORTFOLIO_STOCK_INSERT = "INSERT INTO portfolio_stock (symbol, p_id) VALUES (?, ?)";
    public static final String SQL_PORTFOLIO_STOCK_DELETE_BY_ID = "DELETE FROM portfolio_stock WHERE ps_id = ?";
    public static final String SQL_PORTFOLIO_STOCK_TRANSACTION_SELECT_ALL = "SELECT * FROM portfolio_stock_transaction WHERE ps_id = ?";
    public static final String SQL_PORTFOLIO_STOCK_TRANSACTION_SELECT_BY_ID = "SELECT * FROM portfolio_stock_transaction WHERE pst_id = ?";
    public static final String SQL_PORTFOLIO_STOCK_TRANSACTION_INSERT = "INSERT INTO portfolio_stock_transaction (quantity, price, brokerage_fees, tx_datetime, ps_id) VALUES (?, ?, ?, CONVERT_TZ(STR_TO_DATE(?, '%s'), ?, '+00:00'), ?)".formatted(DATE_FORMAT_STR);
    public static final String SQL_PORTFOLIO_STOCK_TRANSACTION_UPDATE_BY_ID = "UPDATE portfolio_stock_transaction SET quantity = ?, price = ?, brokerage_fees = ?, tx_datetime = CONVERT_TZ(STR_TO_DATE(?, '%s'), ?, '+00:00') WHERE pst_id = ?".formatted(DATE_FORMAT_STR);
    public static final String SQL_PORTFOLIO_STOCK_TRANSACTION_DELETE_BY_ID = "DELETE FROM portfolio_stock_transaction WHERE pst_id = ?";
}
