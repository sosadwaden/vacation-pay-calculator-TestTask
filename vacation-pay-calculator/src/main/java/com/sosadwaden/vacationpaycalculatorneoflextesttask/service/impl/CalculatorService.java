package com.sosadwaden.vacationpaycalculatorneoflextesttask.service.impl;

import com.sosadwaden.vacationpaycalculatorneoflextesttask.dto.VacationPayResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CalculatorService {

    VacationPayResponse getVacationPayCalculation(BigDecimal averageSalaryPerYear, int vacationDays);

    VacationPayResponse getPaidVacationDays(BigDecimal averageSalaryPerYear, LocalDate startDateOfVacation, LocalDate endDateOfVacation);
}
