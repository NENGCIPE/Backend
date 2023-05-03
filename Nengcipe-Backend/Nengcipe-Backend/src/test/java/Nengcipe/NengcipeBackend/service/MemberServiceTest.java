package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Test
    @DisplayName("회원 가입 테스트")
    void registerMemberTest() {
        MemberDto member = MemberDto.builder()
                .memberId("abcd")
                .password("123456").build();
        Member member1 = memberService.registerMember(member);
        Assertions.assertThat(member1.getMemberId()).isEqualTo(member.getMemberId());
    }
}