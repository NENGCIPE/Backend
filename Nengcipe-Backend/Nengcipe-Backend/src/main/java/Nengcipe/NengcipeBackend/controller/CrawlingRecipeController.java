package Nengcipe.NengcipeBackend.controller;

import Nengcipe.NengcipeBackend.dto.ResultResponse;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.service.CrawlingRecipeService;
import Nengcipe.NengcipeBackend.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
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

    @GetMapping("/all")
    public ResponseEntity<ResultResponse> getMatchingRecipes(HttpServletRequest request) throws NotFoundException {
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("레시피 목록 로드 성공.")
                .result(recipeService.findMatchingRecipes()).build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
