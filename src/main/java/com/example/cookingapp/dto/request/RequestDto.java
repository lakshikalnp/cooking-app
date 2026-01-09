package com.example.cookingapp.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    @NotNull(message = "people is required")
    @Min(value = 1, message = "people must be at least 1")
    private Integer people;

    @NotBlank(message = "prompt must not be blank")
    private String prompt;

    @Min(value = 1, message = "noOfGramsOnePersonEats must be greater than 0")
    private Integer noOfGramsOnePersonEats;

    @NotNull(message = "isCustomized is required")
    private Boolean isCustomized;

    @AssertTrue(message = "noOfGramsOnePersonEats is required when isCustomized is true")
    public boolean isCustomizationValid() {
        if (Boolean.TRUE.equals(isCustomized)) {
            return noOfGramsOnePersonEats != null;
        }
        return true;
    }
}
