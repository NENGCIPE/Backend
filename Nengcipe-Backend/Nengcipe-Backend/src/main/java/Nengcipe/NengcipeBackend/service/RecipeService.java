package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Category;
import Nengcipe.NengcipeBackend.domain.Ingredient;
import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.dto.IngredientDto;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.repository.IngredientRepository;
import Nengcipe.NengcipeBackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public List<Recipe> findMatchingRecipes(Member member, List<Ingredient> ingredient) throws NotFoundException {

        List<Recipe> CrawlingRecipeList = recipeRepository.findAll();

        /*
        *   Optional<Ingredient> ingredNameList = ingredientRepository.findByIngredNameAndMember(member);
        *   member.getRecipeList를 인자로 받으므로, 아래에서 for문을 돌며 각 ingredient 객체 마다 .getIngredName을 해준다.
        */

       List<Recipe> matchingRecipes = new ArrayList<>();

        for (Recipe recipeIngredient : CrawlingRecipeList) {
            boolean flag = false;
            for (Ingredient ingredientNameList: ingredient) {
                StringBuilder recipeIngredNameList = recipeIngredient.getRecipeIngredName();
                String[] values = recipeIngredNameList.toString().split(",");
                for (String value : values) {
                    if (value.equals(ingredientNameList.getIngredName())) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag == true) {
                matchingRecipes.add(recipeIngredient);

            }
        }
        return matchingRecipes;
    }
}
