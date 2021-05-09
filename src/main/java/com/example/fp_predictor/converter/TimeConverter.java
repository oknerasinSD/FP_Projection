package com.example.fp_predictor.converter;

import java.sql.Time;
import java.time.LocalTime;

/**
 * Конвертер объектов представления времени.
 */
public class TimeConverter {

    /**
     * Конвертирование объекта типа String в формат для представления в БД.
     * @param stringTime - конвертируемый объект.
     * @return - объект типа java.sql.Time.
     */
    public Time toDatabase(String stringTime) {
        LocalTime localTime = LocalTime.parse(stringTime);
        if (localTime != null) {
            return Time.valueOf(localTime);
        } else {
            return null;
        }
    }


    /**
     * Конвертирование объекта java.sql.Time в LocalTime.
     * @param sqlTime - конвертируемый объект.
     * @return - объект типа LocalTime.
     */
    public LocalTime fromDatabase(Time sqlTime) {
        if (sqlTime != null) {
            return sqlTime.toLocalTime();
        } else {
            return null;
        }
    }
}