package ibf.paf.portfolio.models;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ibf.paf.portfolio.utils.PosixSecondsSerializer;

public record Dividend(
                float value,
                @JsonSerialize(using = PosixSecondsSerializer.class) BigDecimal timestamp,
                ZonedDateTime datetime) {

}
