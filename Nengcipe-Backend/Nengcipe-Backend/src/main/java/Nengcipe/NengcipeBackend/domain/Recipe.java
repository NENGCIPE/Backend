package Nengcipe.NengcipeBackend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    private String recipeName;
    private String recipeDetail;
    private StringBuilder recipeIngredName;
    private StringBuilder recipeIngredAmount;
    private String imgUrl;

    @OneToMany(mappedBy = "recipe")
    private List<MemberRecipe> memberRecipeList = new ArrayList<>();

// 이 Recipe 생성 후 RecipeIngredient의 인자로 넣기. 스크랩 할 때는 MemberRecipe의 인자로 넣기
    @Builder
    public Recipe(String recipeName, String recipeDetail, StringBuilder recipeIngredName, StringBuilder recipeIngredAmount, String imgUrl) {
        this.recipeName = recipeName;
        this.recipeIngredName = recipeIngredName;
        this.recipeDetail = recipeDetail;
        this.recipeIngredAmount = recipeIngredAmount;
        this.imgUrl = imgUrl;
    }

    /*
    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }
    public void setRecipeDetail(String recipeDetail) { this.recipeDetail = recipeDetail;}
    public void setRecipeIngredName(String recipeIngredName) { this.recipeIngredName = recipeIngredName; }
    public void setRecipeIngredAmount(String recipeIngredAmount) { this.recipeIngredAmount = recipeIngredAmount; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
    */
}
