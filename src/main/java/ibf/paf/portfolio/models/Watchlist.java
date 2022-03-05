package ibf.paf.portfolio.models;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Watchlist {
    private int wId;
    private String wName;
    private List<ShareCounter> stocks;
    private int uId;

    public int getwId() {
        return wId;
    }

    public void setwId(int wId) {
        this.wId = wId;
    }

    public String getwName() {
        return wName;
    }

    public void setwName(String wName) {
        this.wName = wName;
    }

    public List<ShareCounter> getStocks() {
        return stocks;
    }

    public void setStocks(List<ShareCounter> stocks) {
        this.stocks = stocks;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public static Watchlist populate(SqlRowSet rs) {
        Watchlist watchlist = new Watchlist();
        watchlist.setwId(rs.getInt("w_id"));
        watchlist.setwName(rs.getString("w_name"));
        watchlist.setuId(rs.getInt("u_id"));
        return watchlist;
    }

    @Override
    public String toString() {
        return "Watchlist [stocks=" + stocks + ", uId=" + uId + ", wId=" + wId + ", wName=" + wName + "]";
    }
}
