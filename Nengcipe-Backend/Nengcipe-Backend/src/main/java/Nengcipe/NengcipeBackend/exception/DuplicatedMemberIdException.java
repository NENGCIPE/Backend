package Nengcipe.NengcipeBackend.exception;

import Nengcipe.NengcipeBackend.domain.MemberResponseDto;
import lombok.Getter;

@Getter
public class DuplicatedMemberIdException extends RuntimeException {
    private MemberResponseDto memberReq;
    public DuplicatedMemberIdException(String message, MemberResponseDto memberReq) {
        super(message);
        this.memberReq=memberReq;
    }
}
