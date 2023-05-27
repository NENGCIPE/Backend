package Nengcipe.NengcipeBackend.controller;

import Nengcipe.NengcipeBackend.domain.Ingredient;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.service.CrawlingRecipeService;
import Nengcipe.NengcipeBackend.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class CrawlingRecipeController {

    private final CrawlingRecipeService crawlingRecipeService;
    private final RecipeService recipeService;

/*
    @GetMapping("/all")
    public ResponseEntity<String> scrapAndSaveRecipes(Recipe recipe) {
        crawlingRecipeService.crawlingRecipes();
        return ResponseEntity.ok("done.");
    }
*/

/*
    @GetMapping("/all")
    public ResponseEntity<List> getRecipeByIngredient(@RequestParam List<String> ingredientNames) {
    }
*/
}
