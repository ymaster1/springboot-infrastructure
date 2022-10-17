package cn.me.ppx.infrastructure.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author panda
 * 该类表示为给bigDecimal转换为人民币形式转换，保留两位小数
 */
public class JsonBigDecimalSerializer extends JsonSerializer<BigDecimal> {
    private static final DecimalFormat DECIMAL_FORMAT = new  DecimalFormat("#####0.00");
    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DECIMAL_FORMAT.format(bigDecimal));
    }
}
