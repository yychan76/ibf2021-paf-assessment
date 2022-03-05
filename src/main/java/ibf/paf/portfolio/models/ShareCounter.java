package ibf.paf.portfolio.models;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class ShareCounter {
    private String symbol;
    private String name;
    private String type;
    private String region;
    private String currency;
    private float currentPrice;
    private List<Price> prices;
    private List<Dividend> dividends;
    private List<Split> splits;
    private int wsId;
    private int wId;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public List<Dividend> getDividends() {
        return dividends;
    }

    public void setDividends(List<Dividend> dividends) {
        this.dividends = dividends;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;
    }

    public int getwsId() {
        return wsId;
    }

    public void setwsId(int wsId) {
        this.wsId = wsId;
    }

    public int getwId() {
        return wId;
    }

    public void setwId(int wId) {
        this.wId = wId;
    }

    public static ShareCounter populate(SqlRowSet rs) {
        ShareCounter watchlist = new ShareCounter();
        watchlist.setwsId(rs.getInt("ws_id"));
        watchlist.setSymbol(rs.getString("symbol"));
        watchlist.setwId(rs.getInt("w_id"));
        return watchlist;
    }
}
