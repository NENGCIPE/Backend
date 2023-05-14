package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.dto.MemberDto;
import Nengcipe.NengcipeBackend.dto.MemberResponseDto;
import Nengcipe.NengcipeBackend.exception.*;
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
            throw new DuplicationException("유저 아이디", memberReq);

        }
        memberDto.setPassword(encoder.encode(memberDto.getPassword()));
        Member member = MemberDto.toEntity(memberDto);

        return memberRepository.save(member);
    }

    public Member deleteMember(Long id) throws NotFoundException {
        Optional<Member> findMember = memberRepository.findById(id);
        if (findMember.isEmpty()) {
            MemberResponseDto memberReq = MemberResponseDto.of(findMember.get());
            throw new NotFoundException("유저 아이디", memberReq);
        }
        memberRepository.delete(findMember.get());
        return findMember.get();
    }

    public Member findByMemberId(String memberId) throws NotFoundException {
        Optional<Member> findMember = memberRepository.findByMemberId(memberId);
        if (findMember.isEmpty()) {
            MemberResponseDto memberReq = MemberResponseDto.of(findMember.get());
            throw new NotFoundException("유저 아이디", memberReq);
        }
        return findMember.get();
    }
    public Member findById(Long id) throws NotFoundException {
        Optional<Member> findMember = memberRepository.findById(id);
        if (findMember.isEmpty()) {
            MemberResponseDto memberReq = MemberResponseDto.of(findMember.get());
            throw new NotFoundException("유저 아이디", memberReq);
        }
        return findMember.get();
    }

    public String login(String memberId, String memberPwd) throws NotFoundException {
        Optional<Member> findMember = memberRepository.findByMemberId(memberId);
        if (findMember.isEmpty()) {
            MemberResponseDto memberReq = MemberResponseDto.builder()
                    .memberId(memberId).build();
            throw new NotFoundException("유저 아이디", memberReq);
        }
        Member member = findMember.get();
        if (encoder.matches(memberPwd, member.getPassword())) {
            return "로그인 성공";
        }
        throw new MemberPwdException("비밀번호가 일치하지 않습니다.");

    }


}
