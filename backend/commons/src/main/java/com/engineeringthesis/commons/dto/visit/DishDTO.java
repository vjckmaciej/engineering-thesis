package com.engineeringthesis.commons.dto.visit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDTO {
    private String dishName;
    private String mealName;
    private Integer dishCaloriesNumber;
    private List<String> quantityWithIngredient;
}
