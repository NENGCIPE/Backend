package Nengcipe.NengcipeBackend.exception;

import Nengcipe.NengcipeBackend.domain.MemberResponseDto;
import lombok.Getter;

@Getter
public class MemberNotFoundException extends ClassNotFoundException{
    private MemberResponseDto memberReq;
    public MemberNotFoundException(String message, MemberResponseDto memberReq) {
        super(message);
        this.memberReq=memberReq;
    }
}
