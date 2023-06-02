package Nengcipe.NengcipeBackend.dto;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IngredientDto {
    private String ingredName;
    private String categoryName;
    private Integer quantity;

}
