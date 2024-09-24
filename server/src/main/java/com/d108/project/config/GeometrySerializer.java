package com.d108.project.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTWriter;

import java.io.IOException;

public class GeometrySerializer extends StdSerializer<Geometry> {

    public GeometrySerializer() {
        super(Geometry.class);
    }

    @Override
    public void serialize(Geometry value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        WKTWriter writer = new WKTWriter();
        String wkt = writer.write(value); // Geometry 객체를 WKT 문자열로 변환
        gen.writeString(wkt); // JSON에서 문자열로 직렬화
    }
}
