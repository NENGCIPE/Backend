package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.repository.RecipeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrawlingRecipeServiceTest {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    CrawlingRecipeService crawlingRecipeService;

    @Test
    @DisplayName("DB에 올려보자.")
    public void DBtest() {
        crawlingRecipeService.crawlingRecipes();


/*
        Recipe recipe = new Recipe();
        recipeService.crawlingRecipes(recipe);
        Recipe save = recipeRepository.save(recipe);
        System.out.println("save.getRecipeName() = " + save.getRecipeName());*/

//        recipeRepository .findById(save.getId());
//        System.out.println("save.getImgUrl() = " + save.getImgUrl());
//        recipe = recipeService.crawlingRecipes(recipe);
//        recipeRepository.save(recipe);
    }
}
