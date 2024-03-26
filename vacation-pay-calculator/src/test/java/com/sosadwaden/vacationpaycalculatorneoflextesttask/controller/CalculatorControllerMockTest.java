package com.sosadwaden.vacationpaycalculatorneoflextesttask.controller;

import com.sosadwaden.vacationpaycalculatorneoflextesttask.dto.VacationPayResponse;
import com.sosadwaden.vacationpaycalculatorneoflextesttask.service.CalculatorServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(CalculatorController.class)
class CalculatorControllerMockTest {

    public static final String API = "/calculate";

    @Autowired
    protected MockMvc mvc;

    @MockBean
    CalculatorServiceImpl calculatorService;

    @BeforeEach
    void beforeEach() {
        log.info("Test start");
    }

    @AfterEach
    void afterEach() {
        log.info("Test end");
    }

    @Test
    @ApiOperation(value = "Test for calculating the amount of vacation pay, knowing the average salary and the number of vacation days")
    void vacationPayCalculationTest() throws Exception {
        Mockito.when(this.calculatorService.getVacationPayCalculation(BigDecimal.valueOf(20000.00), 28))
                .thenReturn(VacationPayResponse.builder()
                        .message("The amount of vacation pay (without NDFL)")
                        .vacationPay(BigDecimal.valueOf(16627.52))
                        .build());

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(API)
                .param("averageSalary", String.valueOf(20000.00))
                .param("vacationDays", String.valueOf(28))
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vacationPay").value(BigDecimal.valueOf(16627.52)))
                .andReturn();

        log.info(result.getResponse().getContentAsString());

    }

    @Test
    @ApiOperation(value = "Test for calculating vacation pay by average salary, start and end date of vacation")
    void paidVacationDaysTest() throws Exception {
        Mockito.when(this.calculatorService.getPaidVacationDays(BigDecimal.valueOf(40000),
                        LocalDate.of(2024, 2, 25),
                        LocalDate.of(2024, 3, 24)))
                .thenReturn(VacationPayResponse.builder()
                        .message("The amount of vacation pay (without NDFL)")
                        .vacationPay(BigDecimal.valueOf(22566.61))
                        .build());

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(API)
                .param("averageSalary", String.valueOf(40000))
                .param("startDateOfVacation", "2024-02-25")
                .param("endDateOfVacation", "2024-03-24")
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vacationPay").value(22566.61))
                .andReturn();

        log.info(result.getResponse().getContentAsString());
    }


}
