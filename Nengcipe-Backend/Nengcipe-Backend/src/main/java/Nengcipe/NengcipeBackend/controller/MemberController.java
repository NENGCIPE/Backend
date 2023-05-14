package Nengcipe.NengcipeBackend.controller;

import Nengcipe.NengcipeBackend.domain.*;
import Nengcipe.NengcipeBackend.dto.*;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.service.CategoryService;
import Nengcipe.NengcipeBackend.service.IngredientService;
import Nengcipe.NengcipeBackend.service.MemberService;
import Nengcipe.NengcipeBackend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final IngredientService ingredientService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResultResponse> registerMember(@Valid @RequestBody MemberDto memberDto) {
        Member member = memberService.registerMember(memberDto);
        MemberResponseDto memberResponseDto = MemberResponseDto.of(member);
        log.info("id : {} 생성 완료", memberResponseDto.getMemberId());
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("회원가입이 완료되었습니다.")
                .result(memberResponseDto)
                .build();
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @DeleteMapping
    public ResponseEntity<ResultResponse> deleteMember(HttpServletRequest request) throws NotFoundException {
        String token = (String) request.getAttribute("token");
        Long id = jwtUtil.getId(token); //멤버 PK 가져옴
        Member member = memberService.deleteMember(id);
        MemberResponseDto memberResponseDto = MemberResponseDto.of(member);
        log.info("id : {} 유저 탈퇴",id);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("회원 탈퇴가 완료되었습니다.")
                .result(memberResponseDto)
                .build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /*
        냉장고 재료 관련 api
     */
    @GetMapping("/fridge")
    public ResponseEntity<ResultResponse> getMyRefrigerator(HttpServletRequest request) throws NotFoundException {
        String token = (String) request.getAttribute("token");
        Long id = jwtUtil.getId(token); //멤버 PK 가져옴
        Member member = memberService.findById(id);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("내 냉장고 정보 가져오기 성공.")
                .result(member.getIngredientList()).build();
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @PostMapping("/fridge")
    public ResponseEntity<ResultResponse> registerIngredient(@RequestBody IngredientDto ingredientDto, HttpServletRequest request) throws NotFoundException {
        //이후 프론트로부터 받은 사진을 네이버 OCR API로 보내는 로직을 작성해야한다.
        //OCR API로 부터 정보들을 받으면 레포지토리에 저장한다.
        String token = (String) request.getAttribute("token");
        Long id = jwtUtil.getId(token); //멤버 PK 가져옴
        Member member = memberService.findById(id);
        Category category = categoryService.findCategoryByName(ingredientDto.getCategoryName());
        Ingredient ingredient = ingredientService.registerIngredient(ingredientDto, member, category);
        log.info("name : {} 등록 완료", ingredient.getIngredName());
        IngredientResponseDto response = IngredientResponseDto.of(ingredient);

        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("냉장고에 재료 저장 성공.")
                .result(response).build();
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @DeleteMapping("/fridge")
    public ResponseEntity<ResultResponse> deleteIngredient(@RequestParam Long id, HttpServletRequest request) throws NotFoundException {
        String token = (String) request.getAttribute("token");
        Long memberId = jwtUtil.getId(token); //멤버 PK 가져옴
        Member member = memberService.findById(memberId);

        //권한 체크는 서비스 계층에서
        Ingredient ingredient = ingredientService.deleteIngredient(member, id);
        IngredientResponseDto response = IngredientResponseDto.of(ingredient);

        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("냉장고에 재료 삭제 성공.")
                .result(response).build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }



    @GetMapping("/auth")
    public String authPractice() {

        return "auth";
    }
}
