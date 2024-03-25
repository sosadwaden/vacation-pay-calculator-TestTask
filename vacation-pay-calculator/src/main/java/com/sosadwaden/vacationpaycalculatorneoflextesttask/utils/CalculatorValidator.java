package com.sosadwaden.vacationpaycalculatorneoflextesttask.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class CalculatorValidator {

    public static boolean checkAverageSalaryPerYear(BigDecimal averageSalaryPerYear) {
        return (averageSalaryPerYear != null && averageSalaryPerYear.compareTo(BigDecimal.ZERO) > 0);
    }

    public static boolean checkVacationDays(Integer vacationDays) {
        return (vacationDays != null && vacationDays > 0);
    }

    public static boolean checkStartAndDateDateOfVacation(LocalDate startDateOfVacation, LocalDate endDateOfVacation) {
        return (startDateOfVacation != null) &&
               (endDateOfVacation != null && endDateOfVacation.isAfter(startDateOfVacation));
    }

}
