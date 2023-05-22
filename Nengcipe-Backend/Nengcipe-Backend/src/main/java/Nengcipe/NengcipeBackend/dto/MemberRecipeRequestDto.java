package Nengcipe.NengcipeBackend.dto;

public class MemberRecipeRequestDto {
    private Long memberId;
    private Long recipeId;


    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }


}
