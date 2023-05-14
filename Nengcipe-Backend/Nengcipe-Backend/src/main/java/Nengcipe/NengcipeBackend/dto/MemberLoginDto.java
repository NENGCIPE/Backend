package Nengcipe.NengcipeBackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDto {
    @NotNull(message = "아이디는 필수값입니다.")
    private String memberId;
    @NotNull(message = "비밀번호는 필수값입니다.")
    private String password;

}
