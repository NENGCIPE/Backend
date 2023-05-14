package Nengcipe.NengcipeBackend.dto;

import Nengcipe.NengcipeBackend.domain.Member;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    @NotNull(message="이름은 필수 값입니다.")
    private String memberName;
    @NotNull(message = "아이디는 필수값입니다.")
    private String memberId;
    @NotNull(message = "비밀번호는 필수값입니다.")
    private String password;

    public static Member toEntity(MemberDto member) {
        return Member.builder()
                .memberName(member.memberName)
                .memberId(member.memberId)
                .password(member.password)
                .build();
    }
}
