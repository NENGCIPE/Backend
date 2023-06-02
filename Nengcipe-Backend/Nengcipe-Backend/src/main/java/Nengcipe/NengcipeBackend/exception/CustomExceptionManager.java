package Nengcipe.NengcipeBackend.exception;

import Nengcipe.NengcipeBackend.dto.ResultResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class CustomExceptionManager {
    ObjectMapper mapper = new ObjectMapper();
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultResponse> requestException(MethodArgumentNotValidException ex, BindingResult bindingResult) throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            System.out.println("error = " + error);
            System.out.println("error = " + error.getArguments());
        }
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(bindingResult.getAllErrors().get(0).getDefaultMessage())
                .build();

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(MemberNotFoundException.class)
//    public ResponseEntity<ResultResponse> memberNotFound(MemberNotFoundException ex) {
//        ResultResponse res = ResultResponse.builder()
//                .code(HttpStatus.NOT_FOUND.value())
//                .message(ex.getMessage())
//                .result(ex.getMemberReq())
//                .build();
//        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
//    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResultResponse> notFoundException(NotFoundException ex) {
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .result(ex.getObject())
                .build();
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
//    @ExceptionHandler(DuplicatedMemberIdException.class)
//    public ResponseEntity<ResultResponse> duplicatedMemberId(DuplicatedMemberIdException ex) {
//        ResultResponse res = ResultResponse.builder()
//                .code(HttpStatus.CONFLICT.value())
//                .message(ex.getMessage())
//                .result(ex.getMemberReq())
//                .build();
//        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
//    }

//    //카테고리 이름 중복
//    @ExceptionHandler(DuplicatedMemberIdException.class)
//    public ResponseEntity<ResultResponse> duplicatedCategoryName(DuplicatedCategoryNameException ex) {
//        ResultResponse res = ResultResponse.builder()
//                .code(HttpStatus.CONFLICT.value())
//                .message(ex.getMessage())
//                .result(ex.getCategoryDto())
//                .build();
//        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
//    }
    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ResultResponse> duplicationException(DuplicationException ex) {
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .result(ex.getObject())
                .build();
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MemberPwdException.class)
    public ResponseEntity<ResultResponse> pwdException(MemberPwdException ex) {

        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ResultResponse> pwdException(NotAuthorizedException ex) {

        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
    }
}
