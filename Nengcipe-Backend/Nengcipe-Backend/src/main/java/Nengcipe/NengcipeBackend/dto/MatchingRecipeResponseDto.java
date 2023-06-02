package Nengcipe.NengcipeBackend.dto;


import Nengcipe.NengcipeBackend.domain.Recipe;
import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingRecipeResponseDto{

    private String recipeName;
    private String recipeDetail;
    private StringBuilder recipeIngredName;
    private StringBuilder recipeIngredAmount;
    private String imgUrl;

    public static MatchingRecipeResponseDto of(Recipe recipe) {
        return MatchingRecipeResponseDto.builder()
                .recipeName(recipe.getRecipeName())
                .recipeDetail(recipe.getRecipeDetail())
                .recipeIngredName(recipe.getRecipeIngredName())
                .recipeIngredAmount(recipe.getRecipeIngredAmount())
                .imgUrl(recipe.getImgUrl())
                .build();
    }
}
