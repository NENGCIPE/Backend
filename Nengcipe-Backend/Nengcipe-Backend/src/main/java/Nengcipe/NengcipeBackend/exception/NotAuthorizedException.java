package Nengcipe.NengcipeBackend.exception;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException() {
        super("접근 권한 없음");
    }
}
