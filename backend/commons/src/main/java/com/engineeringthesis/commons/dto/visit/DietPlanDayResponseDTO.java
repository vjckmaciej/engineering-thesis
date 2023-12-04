package com.engineeringthesis.commons.dto.visit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietPlanDayResponseDTO {
    private String dayName;
    private List<DishDTO> dishDTO;
    private String choiceReason;
}
