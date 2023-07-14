package br.com.banco.repositories.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimestampConverterTest {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");

    private TimestampConverter converter;

    @BeforeEach
    void setUp() {
        converter = new TimestampConverter();
    }

    @Test
    void shouldConvertToNullDatabaseColumnWhenNull() {
        final String result = converter.convertToDatabaseColumn(null);

        assertNull(result);
    }

    @Test
    void shouldConvertToDatabaseColumn() throws Exception {
        final String result = converter.convertToDatabaseColumn(new Timestamp(DATE_FORMAT.parse("2020-06-08 07:15:01-03").getTime()));

        assertEquals("2020-06-08 07:15:01-03", result);
    }

    @Test
    void shouldConvertToNullEntityAttributeWhenNull() {
        final Timestamp result = converter.convertToEntityAttribute(null);

        assertNull(result);
    }

    @Test
    void shouldConvertToEntityAttribute() throws Exception {
        final Timestamp result = converter.convertToEntityAttribute("2021-04-01 12:12:04+03");

        assertEquals(new Timestamp(DATE_FORMAT.parse("2021-04-01 12:12:04+03").getTime()), result);
    }

    @Test
    void shouldThrowExceptionWhenConvertionFails() {
        assertThrows(RuntimeException.class, () -> converter.convertToEntityAttribute("INVALID"));
    }
}