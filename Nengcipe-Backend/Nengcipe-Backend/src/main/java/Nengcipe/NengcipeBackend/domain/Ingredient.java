package Nengcipe.NengcipeBackend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Ingredient {
    //냉장고에 저장하는 재료와 레시피에 저장하는 재료 두 가지를 같은 Ingredient 클래스로 사용.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingred_id")
    private Long id;
    private String ingredName;
    private int quantity;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "ingredient")
    private List<IngredientRecipe> ingredientRecipeList = new ArrayList<>();

    /** 냉장고 재료 저장 builder. category는 기존 것 사용. */
    @Builder(builderClassName = "MyIngredient", buildMethodName = "myIngredient")
    public Ingredient(String ingredName, int quantity, Category category, Member member) {
        this.ingredName=ingredName;
        this.quantity=quantity;
        this.category=category;
        this.member=member;
        member.getIngredientList().add(this);
        category.getIngredientList().add(this); //category에 저장.
    }
    /** 레시피 재료 저장 builder. 이 Ingredient 생성 후 IngredientRecipe의 인자로 넣기. */
    @Builder(builderClassName = "RecipeIngredient", buildMethodName = "recipeIngredient")
    public Ingredient(String ingredName, int quantity, Category category) {
        this.ingredName=ingredName;
        this.quantity=quantity;
        this.category=category;
        category.getIngredientList().add(this); //category에 저장.
    }
}
