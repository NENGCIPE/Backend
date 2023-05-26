package Nengcipe.NengcipeBackend.controller;


import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.MemberRecipe;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.dto.MemberRecipeRequestDto;
import Nengcipe.NengcipeBackend.dto.MemberRecipeResponseDto;
import Nengcipe.NengcipeBackend.dto.ResultResponse;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.repository.MemberRecipeRepository;
import Nengcipe.NengcipeBackend.service.MemberRecipeService;
import Nengcipe.NengcipeBackend.service.MemberService;
import Nengcipe.NengcipeBackend.service.RecipeService;
import Nengcipe.NengcipeBackend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/recipes")
@RequiredArgsConstructor
public class MemberRecipeController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final MemberRecipeService memberRecipeService;
    private final RecipeService recipeService;
    private final MemberRecipeRepository memberRecipeRepository;


    /**
     * API : [POST] 레시피 스크랩 API
     * @param memberRecipeRequestDto
     * @param request
     * @return
     * @throws NotFoundException
     * @uri api/recipes/scrap
     */
    @PostMapping("/scrap")
    public ResponseEntity<Object> scrapRecipe(@RequestBody MemberRecipeRequestDto memberRecipeRequestDto, HttpServletRequest request) throws NotFoundException {
        ResultResponse res;
        try {
            String token = (String) request.getAttribute("token");
            Long member_id = jwtUtil.getId(token);
            Member member = memberService.findById(member_id);
            Recipe recipe = recipeService.findRecipeById(memberRecipeRequestDto.getRecipeId());

            Optional<MemberRecipe> find = memberRecipeRepository.findByMemberAndRecipe(member, recipe);
            if (find.isPresent()){
                ResultResponse errRes;
                errRes = ResultResponse.builder()
                        .code(HttpStatus.CONFLICT.value())
                        .message("이미 스크랩된 레시피입니다.")
                        .result(find.get().getRecipe().getId())
                        .build();

                return new ResponseEntity<>(errRes, HttpStatus.CONFLICT);
            }

            MemberRecipe memberRecipe = memberRecipeService.createScrapRecipe(memberRecipeRequestDto,member, recipe);
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

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    /**
     * API : [GET] 레시피 스크랩 목록 API
     * @param request
     * @return
     * @throws NotFoundException
     * @uri api/recipes/scrapList
     */

    @GetMapping("/scrapList")
    public ResponseEntity<ResultResponse> getScrapRecipe(HttpServletRequest request) throws NotFoundException {
        ResultResponse res;
        try {
            String token = (String) request.getAttribute("token");
            Long member_id = jwtUtil.getId(token);
            Member member = memberService.findById(member_id);

            res = ResultResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("레시피 스크랩 목록 성공")
                    .result(member.getMemberRecipeList())
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

    /**
     * API : [Delete] 레시피 스크랩 삭제 API
     *
     * @param memberRecipeRequestDto
     * @param request
     * @return
     * @throws NotFoundException
     * @uri api/recipes/scrapOut
     */
    @DeleteMapping("/scrapOut")
    public ResponseEntity<ResultResponse> deleteScrapRecipe(@RequestBody MemberRecipeRequestDto memberRecipeRequestDto, HttpServletRequest request) throws NotFoundException {
        ResultResponse res;
        try {
            String token = (String) request.getAttribute("token");
            Long member_id = jwtUtil.getId(token);
            Member member = memberService.findById(member_id);
            Recipe recipe = recipeService.findRecipeById(memberRecipeRequestDto.getRecipeId());

            MemberRecipe memberRecipe = memberRecipeService.deleteScrapRecipe(member, recipe);
            MemberRecipeResponseDto response = MemberRecipeResponseDto.of(memberRecipe);

            res = ResultResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("레시피 스크랩삭제 성공")
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
