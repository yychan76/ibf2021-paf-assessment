package ibf.paf.portfolio.models;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Portfolio {
    private int pId;
    private String pName;
    private List<Stock> stocks;
    private int uId;

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public static Portfolio populate(SqlRowSet rs) {
        Portfolio portfolio = new Portfolio();
        portfolio.setpId(rs.getInt("p_id"));
        portfolio.setpName(rs.getString("p_name"));
        portfolio.setuId(rs.getInt("u_id"));
        return portfolio;
    }

    @Override
    public String toString() {
        return "Portfolio [pId=" + pId + ", pName=" + pName + ", stocks=" + stocks + ", uId=" + uId + "]";
    }

}
