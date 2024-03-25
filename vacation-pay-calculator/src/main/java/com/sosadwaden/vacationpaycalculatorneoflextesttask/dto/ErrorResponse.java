package com.sosadwaden.vacationpaycalculatorneoflextesttask.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {

    final String message;
}
