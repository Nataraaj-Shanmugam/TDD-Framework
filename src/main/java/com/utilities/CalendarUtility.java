package com.utilities;

import com.enums.CALENDAR_ENTITY;
import com.enums.CALENDAR_OPERATION;
import com.genericKeywords.NonUIGenericFunction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static testExecutionEngine.TestExecutionEngine.propFile;

public class CalendarUtility {
    private NonUIGenericFunction nonUIGenericFunction = new NonUIGenericFunction();


    private LocalDateTime manipulateDateAndTime(CALENDAR_ENTITY calendarEntity, CALENDAR_OPERATION calendarOperation, int value) {
        if (calendarOperation == CALENDAR_OPERATION.SUBTRACT) value *= -1;

        LocalDateTime localDateTime = LocalDateTime.now();
        switch (calendarEntity) {
            case YEAR -> localDateTime.plusYears(value);
            case MONTH -> localDateTime.plusMonths(value);
            case DAY -> localDateTime.plusDays(value);
            case WEEK -> localDateTime.plusWeeks(value);
            case HOUR -> localDateTime.plusHours(value);
            case MINUTE -> localDateTime.plusMinutes(value);
            case SECOND -> localDateTime.plusSeconds(value);
        }
        return localDateTime;
    }


    public LocalDate manipulateDate(CALENDAR_ENTITY calendarEntity, CALENDAR_OPERATION calendarOperation, int value) {
        if (!(calendarEntity == CALENDAR_ENTITY.YEAR || calendarEntity == CALENDAR_ENTITY.MONTH || calendarEntity == CALENDAR_ENTITY.DAY))
            throw new IllegalArgumentException("Provide any of YEAR, MONTH, or DAY");
        return manipulateDateAndTime(calendarEntity, calendarOperation, value).toLocalDate();
    }


    public LocalTime manipulateTime(CALENDAR_ENTITY calendarEntity, CALENDAR_OPERATION calendarOperation, int value) {
        if (!(calendarEntity == CALENDAR_ENTITY.YEAR || calendarEntity == CALENDAR_ENTITY.MONTH || calendarEntity == CALENDAR_ENTITY.DAY))
            throw new IllegalArgumentException("Provide any of HOUR, MINUTES, or SECONDS");
        return manipulateDateAndTime(calendarEntity, calendarOperation, value).toLocalTime();
    }


}
