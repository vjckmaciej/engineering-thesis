package com.engineeringthesis.commons.dto.visit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDTO {
    private String name;
    private Integer caloriesNumber;
    private List<String> ingredients;
}
