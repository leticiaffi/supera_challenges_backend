package br.com.banco.repositories.converters;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.AttributeConverter;

public class TimestampConverter implements AttributeConverter<Timestamp, String> {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");

    @Override
    public String convertToDatabaseColumn(Timestamp attribute) {
        if (attribute == null) {
            return null;
        }

        return DATE_FORMAT.format(new Date(attribute.getTime()));
    }

    @Override
    public Timestamp convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        try {
            return new Timestamp(DATE_FORMAT.parse(dbData).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
