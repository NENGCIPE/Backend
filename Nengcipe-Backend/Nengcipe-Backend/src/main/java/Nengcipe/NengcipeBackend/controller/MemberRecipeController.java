package Nengcipe.NengcipeBackend.controller;


import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.MemberRecipe;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.dto.MemberRecipeRequestDto;
import Nengcipe.NengcipeBackend.dto.MemberRecipeResponseDto;
import Nengcipe.NengcipeBackend.dto.ResultResponse;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.service.MemberRecipeService;
import Nengcipe.NengcipeBackend.service.MemberService;
import Nengcipe.NengcipeBackend.service.RecipeService;
import Nengcipe.NengcipeBackend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/recipes")
@RequiredArgsConstructor
public class MemberRecipeController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final MemberRecipeService memberRecipeService;
    private final RecipeService recipeService;


    /**
     *  API : [POST] 레시피 스크랩 API
     * @param memberRecipeRequestDto
     * @param request
     * @return
     * @throws NotFoundException
     * @uri api/recipes/scrap?recipe_id=1
     */
    @PostMapping("/scrap")
    public ResponseEntity<Object> scrapRecipe(@RequestBody MemberRecipeRequestDto memberRecipeRequestDto, HttpServletRequest request) throws NotFoundException  {
        ResultResponse res;
        try{
            String token = (String) request.getAttribute("token");
            Long id = jwtUtil.getId(token);
            Member member = memberService.findById(id);
            Recipe recipe = recipeService.findRecipeById(memberRecipeRequestDto.getRecipeId());
            MemberRecipe memberRecipe = memberRecipeService.createScrapRecipe(member, recipe);
            MemberRecipeResponseDto response = MemberRecipeResponseDto.of(memberRecipe);

            res = ResultResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("레시피 스크랩 성공.")
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

        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

//    /**
//     * API : [GET] 레시피 스크랩 목록 API
//     * @uri api/recipes/scrap
//     */
//    @GetMapping("/scrap")
//    public ResponseEntity<ResultResponse> getScrapRecipe(HttpServletRequest request) throws NotFoundException) {
//
//    }

}
