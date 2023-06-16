package Nengcipe.NengcipeBackend.controller;

import Nengcipe.NengcipeBackend.domain.Ingredient;
import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.dto.MatchingRecipeResponseDto;
import Nengcipe.NengcipeBackend.dto.ResultResponse;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.service.*;
import Nengcipe.NengcipeBackend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class CrawlingRecipeController {

    private final RecipeService recipeService;
    private final MemberService memberService;
    private final IngredientService ingredientService;

    private final JwtUtil jwtUtil;

    @GetMapping("/all")
    public ResponseEntity<ResultResponse> getMatchingRecipes(HttpServletRequest request) throws NotFoundException {
        ResultResponse res;
        try {
            String token = (String) request.getAttribute("token");
            Long member_id = jwtUtil.getId(token);
            Member member = memberService.findById(member_id);


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

    @GetMapping("/{recipeId}")
    public ResponseEntity<ResultResponse> getRecipeDetails(HttpServletRequest request, @PathVariable Long recipeId) throws NotFoundException {
        ResultResponse res;
        try {
            Recipe recipe = recipeService.findRecipeById(recipeId);

            MatchingRecipeResponseDto response = MatchingRecipeResponseDto.of(recipe);

            res = ResultResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("레시피 상세 정보 로드 성공.")
                    .result(response).build();

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
