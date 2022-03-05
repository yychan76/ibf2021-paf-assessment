package ibf.paf.portfolio.models;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ibf.paf.portfolio.utils.PosixSecondsSerializer;

public record Price(
                float open,
                float close,
                float low,
                float high,
                int volume,
                @JsonSerialize(using = PosixSecondsSerializer.class) BigDecimal timestamp,
                ZonedDateTime datetime) {

}
