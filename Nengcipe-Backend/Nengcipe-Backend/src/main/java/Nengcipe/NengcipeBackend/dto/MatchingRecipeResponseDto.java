package Nengcipe.NengcipeBackend.dto;


import Nengcipe.NengcipeBackend.domain.Recipe;
import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingRecipeResponseDto{

    private Long recipeId;
    private String recipeName;
    private String recipeDetail;
    private String recipeIngredName;
    private String recipeIngredAmount;
    private String imgUrl;

    public static MatchingRecipeResponseDto of(Recipe recipe) {
        return MatchingRecipeResponseDto.builder()
                .recipeId(recipe.getId())
                .recipeName(recipe.getRecipeName())
                .recipeDetail(recipe.getRecipeDetail())
                .recipeIngredName(recipe.getRecipeIngredName())
                .recipeIngredAmount(recipe.getRecipeIngredAmount())
                .imgUrl(recipe.getImgUrl())
                .build();
    }
}
