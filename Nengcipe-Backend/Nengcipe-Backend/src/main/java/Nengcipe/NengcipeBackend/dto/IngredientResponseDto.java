package Nengcipe.NengcipeBackend.dto;

import Nengcipe.NengcipeBackend.domain.Category;
import Nengcipe.NengcipeBackend.domain.Ingredient;
import Nengcipe.NengcipeBackend.domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class IngredientResponseDto {
    private Long id;
    private String ingredName;
    private Integer quantity;
    private String categoryName;
    private LocalDate expirationDate;
    public static IngredientResponseDto of(Ingredient ingredient) {
        return IngredientResponseDto.builder()
                .id(ingredient.getId())
                .ingredName(ingredient.getIngredName())
                .quantity(ingredient.getQuantity())
                .expirationDate(ingredient.getExpiratioinDate())
                .categoryName(ingredient.getCategory().getCategoryName()).build();
    }
}
