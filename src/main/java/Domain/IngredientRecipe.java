package Domain;

import jakarta.persistence.*;

@Entity
public class IngredientRecipe {

    @Id @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="ingred_id")
    Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name="recipe_id")
    Recipe recipe;

}
