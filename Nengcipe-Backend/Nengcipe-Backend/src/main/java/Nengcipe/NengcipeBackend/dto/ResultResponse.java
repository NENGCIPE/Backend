package Nengcipe.NengcipeBackend.dto;

import Nengcipe.NengcipeBackend.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResultResponse {
    private int code;
    private String message;
    private Object result;
}
