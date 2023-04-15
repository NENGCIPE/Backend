package Nengcipe.NengcipeBackend.controller;

import Nengcipe.NengcipeBackend.domain.*;
import Nengcipe.NengcipeBackend.exception.MemberNotFoundException;
import Nengcipe.NengcipeBackend.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping
    public ResponseEntity<ResultResponse> registerMember(@Valid @RequestBody MemberDto memberDto) {
        Member member = memberService.registerMember(memberDto);
        MemberResponseDto memberResponseDto = MemberResponseDto.of(member);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("회원가입이 완료되었습니다.")
                .result(memberResponseDto)
                .build();
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@Valid @RequestBody MemberLoginDto memberLoginDto) throws MemberNotFoundException {
        String msg = memberService.login(memberLoginDto.getMemberId(), memberLoginDto.getMemberPwd());
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message(msg)
                .build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
