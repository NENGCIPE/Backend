package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Ingredient;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.repository.IngredientRepository;
import Nengcipe.NengcipeBackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;


    List<Ingredient> ingredients = ingredientRepository.findAllByIngredNameIsNotNull();
    List<String> recipeIngredients = recipeRepository.findAllByrecipeIngredNameIsNotNull();

    public List<Recipe> findMatchingRecipes() {

        List<Ingredient> ingredients = ingredientRepository.findAllByIngredNameIsNotNull();
        List<String> recipeIngredients = recipeRepository.findAllByrecipeIngredNameIsNotNull();

        List<Recipe> matchingRecipes = new ArrayList<>();

        for (String recipeIngredient : recipeIngredients) {
            for (Ingredient ingredient : ingredients) {
                if (recipeIngredient.equals(ingredient.getIngredName())) {
                    matchingRecipes.add(ingredient.getRecipe());
                    break;
                }
            }
        }
        return matchingRecipes;
    }
}
