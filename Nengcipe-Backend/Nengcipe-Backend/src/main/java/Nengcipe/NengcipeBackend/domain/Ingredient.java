package Nengcipe.NengcipeBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Ingredient {
    //냉장고에 저장하는 재료와 레시피에 저장하는 재료 두 가지를 같은 Ingredient 클래스로 사용.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingred_id")
    private Long id;
    private String ingredName;
    @Setter
    private int quantity;
    @ManyToOne
    @JsonIgnore
    private Member member;
    @ManyToOne
    @JsonIgnore
    private Category category;
    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    private List<IngredientRecipe> ingredientRecipeList = new ArrayList<>();

    /** 냉장고 재료 저장 builder. category는 기존 것 사용. */
    @Builder(builderClassName = "MyIngredient", builderMethodName = "myIngredient")
    public Ingredient(String ingredName, Integer quantity, Category category, Member member) {
        this.ingredName=ingredName;
        this.quantity=quantity;
        this.category=category;
        this.member=member;
        member.getIngredientList().add(this);
        category.getIngredientList().add(this); //category에 저장.
    }
    /** 레시피 재료 저장 builder. 이 Ingredient 생성 후 IngredientRecipe의 인자로 넣기. */
    @Builder(builderClassName = "RecipeIngredient", builderMethodName = "recipeIngredient")
    public Ingredient(String ingredName, Integer quantity, Category category) {
        this.ingredName=ingredName;
        this.quantity=quantity;
        this.category=category;
        category.getIngredientList().add(this); //category에 저장.
    }
    public void addQuantity() {
        this.quantity=this.quantity+1;
    }

    public void subQuantity() {
        if (this.quantity > 0) {
            this.quantity-=1;

        }
    }
}
