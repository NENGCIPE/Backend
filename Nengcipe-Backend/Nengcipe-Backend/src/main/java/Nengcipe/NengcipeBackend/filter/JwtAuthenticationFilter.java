package Nengcipe.NengcipeBackend.filter;

import Nengcipe.NengcipeBackend.domain.*;
import Nengcipe.NengcipeBackend.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;


    //로그인을 시도할 때 실행
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        MemberDto memberDto = null;
        try {
            memberDto = objectMapper.readValue(request.getInputStream(), MemberDto.class); //request로 들어온 JSON 형식을 MemberDto로 가져옴
        } catch (Exception e) {
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberDto.getMemberId(), memberDto.getMemberPwd());
        try {
            //인증 실행
            Authentication authenticate = authenticationManager.authenticate(token);
            return authenticate;

        } catch (RuntimeException e) {
            //해당 유저가 없으면 에러 반환. 이 부분은 나중에 구체적으로 변경하기.
            //에러 JSON 반환
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            memberDto.setMemberPwd(null);
            ResultResponse res = ResultResponse.builder()
                    .code(HttpServletResponse.SC_NOT_FOUND)
                    .message("해당 유저를 찾지 못했습니다.")
                    .result(memberDto).build();
            try {
                String s = objectMapper.writeValueAsString(res);
                PrintWriter writer = response.getWriter();
                writer.write(s);
                return null;
            } catch (IOException ex) {
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
        response.setHeader("Authorization", jwt);
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