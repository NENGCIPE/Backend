package Nengcipe.NengcipeBackend.filter;

import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.dto.MemberDto;
import Nengcipe.NengcipeBackend.domain.PrincipalDetails;
import Nengcipe.NengcipeBackend.dto.MemberResponseDto;
import Nengcipe.NengcipeBackend.dto.ResultResponse;
import Nengcipe.NengcipeBackend.repository.MemberRepository;
import Nengcipe.NengcipeBackend.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.memberRepository=memberRepository;
        this.jwtUtil=jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader("Authorization");
        //jwt가 없거나 Bearer로 시작하지 않으면 거부
        if (jwt == null || !jwt.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }
        //Bearer를 제거하고 jwt 값만 가져옴
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String memberId = jwtUtil.getMemberId(token);
        //만료 여부 체크
        if (jwtUtil.isExpired(token)) {
            log.info("id : {} 토큰 만료", memberId);
            chain.doFilter(request, response);
            return;
        }
        //이 아래 코드가 실행된다는 뜻은 유효한 토큰이라는 뜻
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        //만약 해당 유저가 없다면 에러 JSON 반환
        if (member.isEmpty()) {
            MemberResponseDto responseDto = MemberResponseDto.builder().memberId(memberId).build();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            log.info("id : {} 해당 유저가 없습니다.", memberId);
            ResultResponse res = ResultResponse.builder()
                    .code(HttpServletResponse.SC_NOT_FOUND)
                    .message("유저 아이디 : 찾지 못 함")
                    .result(responseDto).build();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String s = objectMapper.writeValueAsString(res);
                PrintWriter writer = response.getWriter();
                writer.write(s);
                return;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        PrincipalDetails principalDetails = new PrincipalDetails(member.get());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("id : {} 접근 권한이 존재합니다.", memberId);
        request.setAttribute("token", token);
        chain.doFilter(request, response);
    }

}