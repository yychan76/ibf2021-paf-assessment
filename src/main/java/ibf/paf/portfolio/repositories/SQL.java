package ibf.paf.portfolio.repositories;

public class SQL {

    private SQL() {
        throw new IllegalStateException("Utility class");
    }

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
}
