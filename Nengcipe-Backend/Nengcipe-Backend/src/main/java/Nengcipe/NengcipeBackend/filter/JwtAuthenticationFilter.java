package Nengcipe.NengcipeBackend.filter;

import Nengcipe.NengcipeBackend.auth.PrincipalDetails;
import Nengcipe.NengcipeBackend.domain.*;
import Nengcipe.NengcipeBackend.dto.JwtResponse;
import Nengcipe.NengcipeBackend.dto.MemberDto;
import Nengcipe.NengcipeBackend.dto.MemberResponseDto;
import Nengcipe.NengcipeBackend.dto.ResultResponse;
import Nengcipe.NengcipeBackend.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;


    //로그인을 시도할 때 실행
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        MemberDto memberDto = null;
        ResultResponse res=null;
        try {
            memberDto = objectMapper.readValue(request.getInputStream(), MemberDto.class); //request로 들어온 JSON 형식을 MemberDto로 가져옴
            log.info("id : {} 로그인 시도", memberDto.getMemberId());
            if (memberDto.getMemberId() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                MemberResponseDto memberResponseDto = new MemberResponseDto(memberDto.getMemberId());
                log.info("id : {} 로그인 실패", memberDto.getMemberId());
                res = ResultResponse.builder()
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .message("유저 아이디는 필수 값입니다.")
                        .result(memberResponseDto).build();
                try {
                    String s = objectMapper.writeValueAsString(res);
                    PrintWriter writer = response.getWriter();
                    writer.write(s);
                    return null;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (memberDto.getPassword() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                MemberResponseDto memberResponseDto = new MemberResponseDto(memberDto.getMemberId());
                log.info("id : {} 로그인 실패", memberDto.getMemberId());
                res = ResultResponse.builder()
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .message("비밀번호는 필수 값입니다.")
                        .result(memberResponseDto).build();
                try {
                    String s = objectMapper.writeValueAsString(res);
                    PrintWriter writer = response.getWriter();
                    writer.write(s);
                    return null;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberDto.getMemberId(), memberDto.getPassword());
        try {
            //인증 실행
            Authentication authenticate = authenticationManager.authenticate(token);
            return authenticate;

        } catch (RuntimeException e) {
            //해당 유저가 없으면 에러 반환. 이 부분은 나중에 구체적으로 변경하기.
            //에러 JSON 반환
            System.out.println("e = " + e.getMessage());
            System.out.println("e = " + e.getClass());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            MemberResponseDto memberResponseDto = new MemberResponseDto(memberDto.getMemberId());
            log.info("id : {} 로그인 실패", memberDto.getMemberId());
            res = ResultResponse.builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message(e.getMessage())
                    .result(memberResponseDto).build();
            try {
                String s = objectMapper.writeValueAsString(res);
                PrintWriter writer = response.getWriter();
                writer.write(s);
                return null;
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    //인증을 성공하면 실행
    //response Authorization header에 jwt를 담아서 보내줌
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        Member member = principal.getMember();
        String jwt = jwtUtil.createJwt(member.getMemberId(), member.getId());
        log.info("id : {} 로그인 성공", member.getMemberId());
        //서블릿으로 JSON 응답
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        ResultResponse res = ResultResponse.builder()
                .code(HttpServletResponse.SC_OK)
                .message("로그인 성공")
                .result(new JwtResponse("Bearer "+jwt)).build();
        try {
            String s = objectMapper.writeValueAsString(res);
            PrintWriter writer = response.getWriter();
            writer.write(s);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}