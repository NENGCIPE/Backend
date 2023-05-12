package Nengcipe.NengcipeBackend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class IngredientRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Ingredient ingredient;
    @ManyToOne
    private Recipe recipe;
    /** ingredient와 recipe가 미리 생성되어 있어야 함. */
    @Builder
    public IngredientRecipe(Ingredient ingredient, Recipe recipe) {
        this.ingredient=ingredient;
        this.recipe=recipe;
        ingredient.getIngredientRecipeList().add(this);
        recipe.getIngredientRecipeList().add(this);
    }

}
