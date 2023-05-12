package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Ingredient;
import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.MemberDto;
import Nengcipe.NengcipeBackend.domain.MemberResponseDto;
import Nengcipe.NengcipeBackend.exception.DuplicatedMemberIdException;
import Nengcipe.NengcipeBackend.exception.MemberNotFoundException;
import Nengcipe.NengcipeBackend.exception.MemberPwdException;
import Nengcipe.NengcipeBackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;


    public Member registerMember(MemberDto memberDto) {
        Optional<Member> findMember = memberRepository.findByMemberId(memberDto.getMemberId());
        if (findMember.isPresent()) {
            MemberResponseDto memberReq = MemberResponseDto.of(findMember.get());
            throw new DuplicatedMemberIdException("아이디가 중복됩니다.", memberReq);

        }
        memberDto.setPassword(encoder.encode(memberDto.getPassword()));
        Member member = MemberDto.toEntity(memberDto);

        return memberRepository.save(member);
    }

    public Member findByMemberId(String memberId) throws MemberNotFoundException {
        Optional<Member> findMember = memberRepository.findByMemberId(memberId);
        if (findMember.isEmpty()) {
            MemberResponseDto memberReq = MemberResponseDto.of(findMember.get());
            throw new MemberNotFoundException("해당 유저를 찾을 수가 없습니다.", memberReq);
        }
        return findMember.get();
    }

    public String login(String memberId, String memberPwd) throws MemberNotFoundException {
        Optional<Member> findMember = memberRepository.findByMemberId(memberId);
        if (findMember.isEmpty()) {
            MemberResponseDto memberReq = MemberResponseDto.builder()
                    .memberId(memberId).build();
            throw new MemberNotFoundException("해당 유저를 찾을 수가 없습니다.", memberReq);
        }
        Member member = findMember.get();
        if (encoder.matches(memberPwd, member.getPassword())) {
            return "로그인 성공";
        }
        throw new MemberPwdException("비밀번호가 일치하지 않습니다.");

    }


}
