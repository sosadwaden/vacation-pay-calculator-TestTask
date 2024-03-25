package com.sosadwaden.vacationpaycalculatorneoflextesttask.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CalculatorException extends RuntimeException {

    final String message;
}
