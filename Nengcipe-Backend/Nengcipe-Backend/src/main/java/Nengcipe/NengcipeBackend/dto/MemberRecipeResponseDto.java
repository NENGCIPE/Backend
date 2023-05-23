package Nengcipe.NengcipeBackend.dto;

import Nengcipe.NengcipeBackend.domain.MemberRecipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor

public class MemberRecipeResponseDto {
    private Long id;
    private Long memberId;
    private Long recipeId;

    public static MemberRecipeResponseDto of(MemberRecipe memberRecipe) {
        return MemberRecipeResponseDto.builder()
                .id(memberRecipe.getId())
                .memberId(memberRecipe.getMember().getId())
                .recipeId(memberRecipe.getRecipe().getId())
                .build();
    }
}
