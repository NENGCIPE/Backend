package Nengcipe.NengcipeBackend.dto;

import Nengcipe.NengcipeBackend.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private String memberId;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getMemberId()).build();
    }

}
