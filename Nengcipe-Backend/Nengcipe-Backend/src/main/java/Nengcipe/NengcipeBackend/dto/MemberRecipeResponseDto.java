package Nengcipe.NengcipeBackend.dto;

import Nengcipe.NengcipeBackend.domain.MemberRecipe;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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