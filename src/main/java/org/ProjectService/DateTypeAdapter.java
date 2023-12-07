package org.ProjectService;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Locale;

public class DateTypeAdapter extends TypeAdapter<Date> {
    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            String dateString = format.format(value);
            out.value(dateString);
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in != null) {
            try {
                java.util.Date date = format.parse(in.nextString());
                return new java.sql.Date(date.getTime());
            } catch (ParseException e) {
                throw new JsonSyntaxException(e);
            }
        } else {
            return null;
        }
    }
}