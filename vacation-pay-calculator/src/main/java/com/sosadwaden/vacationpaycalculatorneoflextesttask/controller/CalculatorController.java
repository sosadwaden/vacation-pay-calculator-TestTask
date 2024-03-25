package com.sosadwaden.vacationpaycalculatorneoflextesttask.controller;

import com.sosadwaden.vacationpaycalculatorneoflextesttask.dto.VacationPayResponse;
import com.sosadwaden.vacationpaycalculatorneoflextesttask.service.CalculatorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Tag(name = "Main controller", description = "Controller contains two api points with different parameters: /calculate?averageSalary=...&vacationDays=..., /calculate?averageSalary=...&startDateOfVacation=...$endDateOfVacation=...")
@Validated
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorServiceImpl calculatorService;

    @Operation(
            summary = "Calculates the amount of vacation pay",
            description = "It is necessary to transfer the average salary and the number of vacation days"
    )
    @GetMapping(value = "${application.endpoint.calculate}", params = {"averageSalary", "vacationDays"})
    public ResponseEntity<VacationPayResponse> calculate(@RequestParam("averageSalary") BigDecimal averageSalaryPerYear,
                                                         @RequestParam("vacationDays") Integer vacationDays) {

        return ResponseEntity.ok().body(calculatorService.getVacationPayCalculation(averageSalaryPerYear, vacationDays));
    }

    @Operation(
            summary = "Calculates the amount of vacation pay",
            description = "It is necessary to transfer the average salary and the start and end dates of the vacation"
    )
    @GetMapping(value = "${application.endpoint.calculate}", params = {"averageSalary", "startDateOfVacation", "endDateOfVacation"})
    public ResponseEntity<VacationPayResponse> calculate(@RequestParam("averageSalary") @Min(0) BigDecimal averageSalary,
                                            @RequestParam("startDateOfVacation")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateOfVacation,
                                            @RequestParam("endDateOfVacation")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDateOfVacation) {

        return ResponseEntity.ok().body(calculatorService.getPaidVacationDays(averageSalary, startDateOfVacation, endDateOfVacation));
    }

}
