package ibf.paf.portfolio.utils;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

// by default ZoneDateTime serializes to epoch time
// com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerialize also provides so use that instead
public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {
    @Override
    public void serialize(ZonedDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}
