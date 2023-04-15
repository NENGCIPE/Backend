package Nengcipe.NengcipeBackend.domain;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    @NotNull(message = "아이디는 필수값입니다.")
    private String memberId;
    @NotNull(message = "비밀번호는 필수값입니다.")
    private String memberPwd;

    public static Member toEntity(MemberDto member) {
        return Member.builder()
                .memberId(member.memberId)
                .memberPwd(member.memberPwd)
                .build();
    }
}
