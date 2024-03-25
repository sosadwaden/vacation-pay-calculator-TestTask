package com.sosadwaden.vacationpaycalculatorneoflextesttask.service;

import com.sosadwaden.vacationpaycalculatorneoflextesttask.dto.VacationPayResponse;
import com.sosadwaden.vacationpaycalculatorneoflextesttask.exception.CalculatorException;
import com.sosadwaden.vacationpaycalculatorneoflextesttask.service.impl.CalculatorService;
import com.sosadwaden.vacationpaycalculatorneoflextesttask.utils.CalculatorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {

    private static final double AVERAGE_NUMBER_OF_DAYS_IN_MONTH = 29.3;
    private static final double NDFL = 0.13;
    public static final int CURRENT_YEAR = LocalDate.now().getYear();

    /**
     * Расчёт отпускных при помощи средней годовой зарплаты и количества дней отпуска
     * @param averageSalaryPerYear средняя зарплата за год
     * @param vacationDays         количество дней отпуска
     * @return Сумма отпускных
     */
    @Override
    public VacationPayResponse getVacationPayCalculation(BigDecimal averageSalaryPerYear, int vacationDays) {
        if (!CalculatorValidator.checkAverageSalaryPerYear(averageSalaryPerYear)) {
            throw CalculatorException.builder()
                    .message("Incorrect average salary")
                    .build();
        }

        if (!CalculatorValidator.checkVacationDays(vacationDays)) {
            throw CalculatorException.builder()
                    .message("Incorrect vacation days")
                    .build();
        }

        BigDecimal averageEarningsPerDay = averageSalaryPerYear.divide(BigDecimal.valueOf(AVERAGE_NUMBER_OF_DAYS_IN_MONTH), 2, RoundingMode.HALF_EVEN);

        BigDecimal totalPayWithoutNDFL = averageEarningsPerDay.multiply(BigDecimal.valueOf(vacationDays));

        BigDecimal amountNDFL = totalPayWithoutNDFL.multiply(BigDecimal.valueOf(NDFL)).setScale(0, RoundingMode.HALF_UP);

        BigDecimal totalPay = totalPayWithoutNDFL.subtract(amountNDFL);

        return VacationPayResponse.builder()
                .message("The amount of vacation pay (without NDFL)")
                .vacationPay(totalPay)
                .build();
    }

    /**
     * Расчёт количества оплачиваемых дней в отпуске с учётом праздников и выходных
     * @param startDateOfVacation Дата начала отпуска
     * @param endDateOfVacation   Дата окончания отпуска
     * @return Количество оплачиваемых дней отпуска
     */
    @Override
    public VacationPayResponse getPaidVacationDays(BigDecimal averageSalaryPerYear, LocalDate startDateOfVacation, LocalDate endDateOfVacation) {
        if (!CalculatorValidator.checkAverageSalaryPerYear(averageSalaryPerYear)) {
            throw CalculatorException.builder()
                    .message("Incorrect average salary")
                    .build();
        }

        if (!CalculatorValidator.checkStartAndDateDateOfVacation(startDateOfVacation, endDateOfVacation)) {
            throw CalculatorException.builder()
                    .message("Incorrect start date of vacation or end date of vacation")
                    .build();
        }

        Predicate<LocalDate> holidays = getHolidays()::contains;

        int paidVacationDays = startDateOfVacation.datesUntil(endDateOfVacation.plusDays(1))
                .filter(date -> !(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY))
                .filter(date -> !(holidays.test(date)))
                .toList().size();

        return getVacationPayCalculation(averageSalaryPerYear, paidVacationDays);
    }

    /**
     * Хранение праздничных дней
     * @return Множество дат праздничных дней
     */
    public static Set<LocalDate> getHolidays() {
        Set<LocalDate> holidays = Stream.of(
                convertToLocalDate(1, 1),
                convertToLocalDate(1, 2),
                convertToLocalDate(1, 3),
                convertToLocalDate(1, 4),
                convertToLocalDate(1, 5),
                convertToLocalDate(1, 6),
                convertToLocalDate(1, 7),
                convertToLocalDate(1, 8),
                convertToLocalDate(2, 23),
                convertToLocalDate(3, 8),
                convertToLocalDate(5, 1),
                convertToLocalDate(5, 9),
                convertToLocalDate(6, 12),
                convertToLocalDate(11, 4)
        ).collect(Collectors.toSet());

        return Collections.unmodifiableSet(holidays);
    }

    private static LocalDate convertToLocalDate(int month, int dayOfMonth) {
        return LocalDate.of(CURRENT_YEAR, month, dayOfMonth);
    }
}
