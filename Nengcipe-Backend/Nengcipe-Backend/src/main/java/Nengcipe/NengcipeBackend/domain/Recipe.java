package Nengcipe.NengcipeBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @Column(name = "recipe_id")
    private Long id;
    @Column(length=2000)
    private String recipeName;
    @Column(length = 2000)
    private String recipeDetail;

    @Column(length = 2000)
    private String recipeIngredName;
    @Column(length = 2000)
    private String recipeIngredAmount;
    @Column(length = 2000)
    private String imgUrl;

    @OneToMany(mappedBy = "recipe")
    @JsonIgnore
    private List<MemberRecipe> memberRecipeList = new ArrayList<>();

// 이 Recipe 생성 후 RecipeIngredient의 인자로 넣기. 스크랩 할 때는 MemberRecipe의 인자로 넣기
    @Builder
    public Recipe(String recipeName, String recipeDetail, String recipeIngredName, String recipeIngredAmount, String imgUrl) {
        this.recipeName = recipeName;
        this.recipeIngredName = recipeIngredName;
        this.recipeIngredAmount = recipeIngredAmount;
        this.recipeDetail = recipeDetail;
        this.imgUrl = imgUrl;
    }


    /*
    * @Builder(builderClassName = "MyMemberRecipe" , builderMethodName = "myMemberRecipe")
    public MemberRecipe(Member member, Recipe recipe) {
        this.member=member;
        this.recipe=recipe;
        member.getMemberRecipeList().add(this);
        recipe.getMemberRecipeList().add(this);
    }
*/

}
