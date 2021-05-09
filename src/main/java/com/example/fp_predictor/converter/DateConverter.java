package com.example.fp_predictor.converter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Конвертер объектов представления даты.
 */
public class DateConverter {

    /**
     * Конвертирование объекта String в формат для представления в БД.
     * @param stringDate - конвертируемыый объект.
     * @return - объект типа java.sql.Date.
     */
    public Date toDatabase(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(stringDate, formatter);
        if (localDate != null) {
            return Date.valueOf(localDate);
        } else {
            return null;
        }
    }

    /**
     * Конвертирование объекта java.sql.Date в LocalDate.
     * @param sqlDate - конвертируемый объект.
     * @return - объект типа LocalDate.
     */
    public LocalDate fromDatabase(Date sqlDate) {
        if (sqlDate != null) {
            return sqlDate.toLocalDate();
        } else {
            return null;
        }
    }
}
