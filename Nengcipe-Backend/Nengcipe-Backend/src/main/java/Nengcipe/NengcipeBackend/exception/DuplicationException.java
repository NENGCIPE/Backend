package Nengcipe.NengcipeBackend.exception;

import Nengcipe.NengcipeBackend.dto.CategoryDto;
import lombok.Getter;

@Getter
public class DuplicationException extends RuntimeException {
    private Object object;
    public DuplicationException(String field, Object object) {
        super(field+" : 중복 에러");
        this.object=object;
    }
}
