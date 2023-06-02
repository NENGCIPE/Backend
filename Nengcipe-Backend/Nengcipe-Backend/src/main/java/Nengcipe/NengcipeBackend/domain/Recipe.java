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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    private String recipeName;
    @Lob
    private String recipeDetail;

    @Lob
    private StringBuilder recipeIngredName;
    @Lob
    private StringBuilder recipeIngredAmount;
    @Lob
    private String imgUrl;

    @OneToMany(mappedBy = "recipe")
    @JsonIgnore
    private List<MemberRecipe> memberRecipeList = new ArrayList<>();

// 이 Recipe 생성 후 RecipeIngredient의 인자로 넣기. 스크랩 할 때는 MemberRecipe의 인자로 넣기
    @Builder
    public Recipe(String recipeName, String recipeDetail, StringBuilder recipeIngredName, StringBuilder recipeIngredAmount, String imgUrl) {
        this.recipeName = recipeName;
        this.recipeIngredName = recipeIngredName;
        this.recipeIngredAmount = recipeIngredAmount;
        this.recipeDetail = recipeDetail;
        this.imgUrl = imgUrl;
    }

}
