package ibf.paf.portfolio.models;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Stock extends ShareCounter {
    private float totalQuantity;
    private float totalValue;
    private List<ValueHistory> valueHistory;
    private List<Transaction> transactions;
    private int psId;
    private int pId;

    record ValueHistory(ZonedDateTime datetime, float quantity, float value) {
    }

    public float getTotalQuantity() {
        return totalQuantity;
    }

    private void setTotalQuantity(float totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public float getTotalValue() {
        return totalValue;
    }

    private void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public List<ValueHistory> getValueHistory() {
        return valueHistory;
    }

    private void setValueHistory(List<ValueHistory> valueHistory) {
        this.valueHistory = valueHistory;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        updateTotalQuantity();
        updateTotalValue();
    }

    public int getPsId() {
        return psId;
    }

    public void setPsId(int psId) {
        this.psId = psId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    private void updateTotalQuantity() {
        float qty = 0;
        for (Transaction t : getTransactions()) {
            qty += t.quantity();
        }
        setTotalQuantity(qty);
    }

    private void updateTotalValue() {
        // assume total quantity is already updated
        float total = getCurrentPrice() * totalQuantity;
        setTotalValue(total);
    }

    private void updateValueHistory() {
        List<ValueHistory> valueHistory = new ArrayList<>();
        List<Transaction> transactions = getTransactions();
        ListIterator<Transaction> iter = transactions.listIterator();
        Transaction trans = iter.next();
        float currentQuantity = trans.quantity();
        // TODO for efficiency set the prices starting from the earliest transaction
        for (Price p : getPrices()) {
            float closingPrice = p.close();
            ZonedDateTime priceZonedDateTime = p.datetime();
            LocalDate priceLocalDate = p.datetime().toLocalDate();
            if (priceLocalDate.compareTo(trans.datetime().toLocalDate()) >= 0) {
                ValueHistory history = new ValueHistory(priceZonedDateTime, currentQuantity,
                        currentQuantity * closingPrice);
                valueHistory.add(history);
                // TODO complete the logic for this later
            }
        }
    }

    public static Stock create(SqlRowSet rs) {
        Stock stock = new Stock();
        stock.setPsId(rs.getInt("ps_id"));
        stock.setSymbol(rs.getString("symbol"));
        stock.setpId(rs.getInt("p_id"));
        return stock;
    }

    @Override
    public String toString() {
        return "Stock [pId=" + pId + ", psId=" + psId +  ", symbol=" + getSymbol() + ", totalQuantity=" + totalQuantity + ", totalValue="
                + totalValue + ", transactions=" + transactions + ", valueHistory=" + valueHistory + "]";
    }

}
