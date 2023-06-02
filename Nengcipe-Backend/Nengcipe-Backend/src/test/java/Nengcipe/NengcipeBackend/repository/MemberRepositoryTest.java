package Nengcipe.NengcipeBackend.repository;

import Nengcipe.NengcipeBackend.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("유저 저장 테스트")
    void saveMember() {
        Member member = Member.builder()
                .memberId("abcd")
                .password("123456")
                .build();
        Member save = memberRepository.save(member);
        Assertions.assertThat(member.getMemberId()).isEqualTo(save.getMemberId());

    }


    @Test
    @DisplayName("유저 검색 테스트")
    void findByMemberId() {

        Member member = Member.builder()
                .memberId("abcd")
                .password("123456")
                .build();
        memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findByMemberId(member.getMemberId());
        Assertions.assertThat(findMember.get().getMemberId()).isEqualTo(member.getMemberId());
    }
}