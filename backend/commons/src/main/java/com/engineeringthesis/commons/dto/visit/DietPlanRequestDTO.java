package com.engineeringthesis.commons.dto.visit;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietPlanRequestDTO {
    @NotNull
    private Integer daysNumber;

    @NotNull
    private Integer dishesNumber;
    private String caloriesRange;
    private String allergens;
}
