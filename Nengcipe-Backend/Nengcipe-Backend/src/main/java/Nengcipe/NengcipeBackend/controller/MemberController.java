package Nengcipe.NengcipeBackend.controller;

import Nengcipe.NengcipeBackend.domain.*;
import Nengcipe.NengcipeBackend.exception.MemberNotFoundException;
import Nengcipe.NengcipeBackend.service.MemberService;
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



    @GetMapping("/auth")
    public String authPractice() {
        return "auth";
    }
}
