package Nengcipe.NengcipeBackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResultResponse {
    private int code;
    private String message;
    private Object result;
}
