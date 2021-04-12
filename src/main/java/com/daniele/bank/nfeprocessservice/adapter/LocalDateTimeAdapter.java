package com.daniele.bank.nfeprocessservice.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String date) throws Exception {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    @Override
    public String marshal(LocalDateTime localDateTime) throws Exception {
        return null;
    }
}
