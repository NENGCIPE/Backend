package Nengcipe.NengcipeBackend.controller;

import Nengcipe.NengcipeBackend.domain.Category;
import Nengcipe.NengcipeBackend.domain.Ingredient;
import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.dto.IngredientDto;
import Nengcipe.NengcipeBackend.dto.MatchingRecipeResponseDto;
import Nengcipe.NengcipeBackend.dto.ResultResponse;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.service.*;
import Nengcipe.NengcipeBackend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class CrawlingRecipeController {

    private final CrawlingRecipeService crawlingRecipeService;
    private final RecipeService recipeService;
    private final MemberService memberService;
    private final IngredientService ingredientService;
    private final CategoryService categoryService;

    private final JwtUtil jwtUtil;

/*
    @GetMapping("/all")
    public ResponseEntity<String> scrapAndSaveRecipes(Recipe recipe) {
        crawlingRecipeService.crawlingRecipes();
        return ResponseEntity.ok("done.");
    }
*/
    @GetMapping("/all")
    public ResponseEntity<ResultResponse> getMatchingRecipes(HttpServletRequest request) throws NotFoundException {
        ResultResponse res;
        try {
            String token = (String) request.getAttribute("token");
            Long member_id = jwtUtil.getId(token);
            Member member = memberService.findById(member_id);

            /*  Recipe Crawling하기. */
            crawlingRecipeService.crawlingRecipes(member);

            /*  Crawling 레시피와 재료 비교하여 레시피 목록 나열하기  */
            List<Ingredient> ingredients = member.getIngredientList();
            List<Recipe> matchingRecipes = recipeService.findMatchingRecipes(member, ingredients);
            List<MatchingRecipeResponseDto> response = new ArrayList<>();

            for (Recipe recipe : matchingRecipes) {
                MatchingRecipeResponseDto tmp = MatchingRecipeResponseDto.of(recipe);
                response.add(tmp);
            }

            res = ResultResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("레시피 목록 로드 성공.")
                    .result(response)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("컨트롤러 에러");

            res = ResultResponse.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("서버 에러 발생.")
                    .result(null)
                    .build();
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
