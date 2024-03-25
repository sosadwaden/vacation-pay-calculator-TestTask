package com.sosadwaden.vacationpaycalculatorneoflextesttask.service;

import com.sosadwaden.vacationpaycalculatorneoflextesttask.exception.CalculatorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class CalculatorServiceTest {

    private CalculatorServiceImpl calculatorService;

    private final BigDecimal averageSalaryPerYearTest = BigDecimal.valueOf(40000);
    private final int vacationDaysTest = 28;

    @BeforeEach
    void beforeEach() {
        log.info("Test start");
        calculatorService = new CalculatorServiceImpl();
    }

    @AfterEach
    void afterEach() {
        log.info("Test end");
        calculatorService = null;
    }

    @Test
    //@DisplayName("The test checks vacation pay with an average salary for the year and vacation days")
    void getVacationPayCalculationTest() {
        BigDecimal actualVacationPay = calculatorService.getVacationPayCalculation(averageSalaryPerYearTest, vacationDaysTest).getVacationPay();
        assertEquals(BigDecimal.valueOf(33256.32), actualVacationPay);
    }

    @Test
    void throwCalculatorExceptionWrongDateTest() {
        assertThrows(CalculatorException.class, () -> {
            LocalDate startDateOfVacationTest = LocalDate.of(2024, 1, 1);
            LocalDate endDateOfVacationTest = LocalDate.of(2023, 1, 30);
            calculatorService.getPaidVacationDays(averageSalaryPerYearTest, startDateOfVacationTest, endDateOfVacationTest);
        });
    }

    @Test
    void throwCalculatorExceptionWrongAverageSalaryTest() {
        assertThrows(CalculatorException.class, () -> {
            BigDecimal wrongAverageSalary = BigDecimal.valueOf(-12.000);
            LocalDate startDateOfVacationTest = LocalDate.of(2024, 1, 1);
            LocalDate endDateOfVacationTest = LocalDate.of(2024, 1, 30);
            calculatorService.getPaidVacationDays(wrongAverageSalary, startDateOfVacationTest, endDateOfVacationTest);
        });
    }

    @Test
    void getPaidVacationDaysTest() {
        LocalDate startDateOfVacationTest = LocalDate.of(2024, 1, 1);
        LocalDate endDateOfVacationTest = LocalDate.of(2024, 1, 30);

        BigDecimal actualVacationPay = calculatorService.getPaidVacationDays(averageSalaryPerYearTest, startDateOfVacationTest, endDateOfVacationTest).getVacationPay();
        assertEquals(BigDecimal.valueOf(19003.04), actualVacationPay);
    }
}
