package ibf.paf.portfolio.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import ibf.paf.portfolio.utils.PosixSecondsSerializer;

public record Transaction(
        int pstId,
        float quantity,
        float price,
        float brokerageFees,
        @JsonSerialize(using = PosixSecondsSerializer.class) BigDecimal timestamp,
        // using this JsonFormat would also apply for deserialize which fails, so we need to apply custom format on serialize only
        // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ssxx")
        @JsonSerialize(using = ZonedDateTimeSerializer.class) ZonedDateTime datetime,
        int psId) {

    private static final Logger LOG = Logger.getLogger(Transaction.class.getName());
    public static Optional<Transaction> create(SqlRowSet rs) {
        ZonedDateTime zonedDatetime;
        // LOG.info(rs.getString("tx_datetime"));
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(rs.getString("tx_datetime"),
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            // create zonedDateTime from the MySQL datetime which is stored in UTC +00:00
            zonedDatetime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
            // get timestamp from the zonedDateTime
            BigDecimal timestamp = BigDecimal.valueOf(zonedDatetime.toEpochSecond());
            return Optional.ofNullable(new Transaction(
                    rs.getInt("pst_id"),
                    rs.getFloat("quantity"),
                    rs.getFloat("price"),
                    rs.getFloat("brokerage_fees"),
                    timestamp,
                    zonedDatetime,
                    rs.getInt("ps_id")));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
